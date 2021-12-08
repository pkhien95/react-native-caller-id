//
//  CallDirectoryHandler.m
//  CallDirectoryExt
//
//  Created by Илья Коваценко on 08.12.2021.
//  Copyright © 2021 Facebook. All rights reserved.
//

#import "CallDirectoryHandler.h"

#define DATA_KEY @"CALLER_LIST"
#define APP_GROUP @"com.hienphung.callerid"

@interface Caller : NSObject
@property NSString* name;
@property NSArray<NSNumber*>* numbers;
-(instancetype) initWithDictionary: (NSDictionary*) dictionary;
@end

@implementation Caller
-(instancetype) initWithDictionary: (NSDictionary*) dictionary {
    if (self = [super init]) {
        self.name = dictionary[@"name"];
        self.numbers = dictionary[@"numbers"];
    }
    return self;
}
@end

@interface CallDirectoryHandler () <CXCallDirectoryExtensionContextDelegate>
@end

@implementation CallDirectoryHandler

- (void)beginRequestWithExtensionContext:(CXCallDirectoryExtensionContext *)context {
    context.delegate = self;

    if (context.isIncremental) {
        [context removeAllIdentificationEntries];
    }
    
    [self addAllIdentificationPhoneNumbersToContext:context];
    
    [context completeRequestWithCompletionHandler:nil];
}

- (NSArray*)getCallerList {
    @try {
        NSUserDefaults* userDefaults = [[NSUserDefaults alloc] initWithSuiteName:APP_GROUP];
        NSArray* callerList = [userDefaults arrayForKey:DATA_KEY];
        if (callerList) {
            return callerList;
        }
        return [[NSArray alloc] init];
    }
    @catch(NSException* e) {
        NSLog(@"Failed to get caller list: %@", e.description);
    }
}


- (void)addAllIdentificationPhoneNumbersToContext:(CXCallDirectoryExtensionContext *)context {
    @try {
        NSArray* callerList = [self getCallerList];
        NSMutableDictionary<NSNumber*, NSString*>* labelsKeyedByPhoneNumber = [[NSMutableDictionary alloc] init];
        NSUInteger callersCount = [callerList count];
        if(callersCount > 0) {
            for (NSUInteger i = 0; i < callersCount; i += 1) {
                Caller* caller = [[Caller alloc] initWithDictionary:([callerList objectAtIndex:i])];
                for (NSUInteger j = 0; j < [caller.numbers count]; j++) {
                    NSNumber* number = caller.numbers[j];
                    [labelsKeyedByPhoneNumber setValue:caller.name forKey:number];
                }
            }
        }
        for (NSNumber *phoneNumber in [labelsKeyedByPhoneNumber.allKeys sortedArrayUsingSelector:@selector(compare:)]) {
            NSString *label = labelsKeyedByPhoneNumber[phoneNumber];
            [context addIdentificationEntryWithNextSequentialPhoneNumber:(CXCallDirectoryPhoneNumber)[phoneNumber unsignedLongLongValue] label:label];
        }
    } @catch (NSException* e) {
        NSLog(@"Failed to get caller list: %@", e.description);
    }
    
}

#pragma mark - CXCallDirectoryExtensionContextDelegate

- (void)requestFailedForExtensionContext:(nonnull CXCallDirectoryExtensionContext *)extensionContext withError:(nonnull NSError *)error {
    NSLog(@"Request failed: %@", error.localizedDescription);
}

@end
