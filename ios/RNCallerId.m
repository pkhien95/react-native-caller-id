
#import "RNCallerId.h"

#define DATA_KEY @"CALLER_LIST"
#define DATA_GROUP @"group.samolet.employee"
#define EXTENSION_ID @"com.samolet.employee.CallDirectoryExtension"

@implementation RNCallerId
- (dispatch_queue_t)methodQueue{
    return dispatch_get_main_queue();
}

+ (BOOL)requiresMainQueueSetup {
    return NO;
}

RCT_EXPORT_MODULE()

-(NSError*) buildErrorFromException: (NSException*) exception withErrorCode: (NSInteger)errorCode {
    NSMutableDictionary* info = [NSMutableDictionary dictionary];
    [info setValue:exception.name forKey:@"Name"];
    [info setValue:exception.reason forKey:@"Reason"];
    [info setValue:exception.callStackReturnAddresses forKey:@"CallStack"];
    [info setValue:exception.callStackSymbols forKey:@"CallStackSymbols"];
    [info setValue:exception.userInfo forKey:@"UserInfo"];
    
    NSError *error = [[NSError alloc] initWithDomain:EXTENSION_ID code:errorCode userInfo:info];
    return error;
}

RCT_EXPORT_METHOD(setCallerList: (NSArray*) callerList withResolver: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    @try {
        NSUserDefaults* userDefaults = [[NSUserDefaults alloc] initWithSuiteName:DATA_GROUP];
        [userDefaults setObject:callerList forKey:DATA_KEY];
        [CXCallDirectoryManager.sharedInstance reloadExtensionWithIdentifier:EXTENSION_ID completionHandler:^(NSError * _Nullable error) {
            if(error) {
                reject(@"setCallerList", @"Failed to reload extension", error);
            } else {
                resolve(@true);
            }
        }];
    }
    @catch (NSException* e) {
        NSError* error = [self buildErrorFromException:e withErrorCode: 100];
        reject(@"setCallerList", @"Failed to set caller list", error);
    }
}

RCT_EXPORT_METHOD(getExtensionEnabledStatus: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    // The completionHandler is called twice. This is a workaround
//    __block BOOL hasResult = false;
//    __block int realResult = 0;
    
    [[CXCallDirectoryManager sharedInstance] getEnabledStatusForExtensionWithIdentifier:EXTENSION_ID completionHandler:^(CXCallDirectoryEnabledStatus enabledStatus, NSError * _Nullable error) {
            if (enabledStatus == 0) {
                reject(@"getExtensionEnabledStatus", @"Failed to get extension status", error);
                // Code 0 tells you that there's an error. Common is that the identifierString is wrong.
            } else if (enabledStatus == 1) {
                resolve([NSNumber numberWithInt:enabledStatus]);
                // Code 1 is deactivated extension
            } else if (enabledStatus == 2) {
                resolve([NSNumber numberWithInt:enabledStatus]);
                // Code 1 is an activated extension
            }
        }];
    
//    [CXCallDirectoryManager.sharedInstance getEnabledStatusForExtensionWithIdentifier:EXTENSION_ID completionHandler:^(CXCallDirectoryEnabledStatus enabledStatus, NSError * _Nullable error) {
//        // TODO: Remove these conditions when you find a way to return the correct result or Apple just fix their bug.
//        if (hasResult == false) {
//            hasResult = true;
//            realResult = (int)enabledStatus;
//        }
//        if(error) {
//            reject(@"getExtensionEnabledStatus", @"Failed to get extension status", error);
//        } else {
//            resolve([NSNumber numberWithInt:realResult]);
//        }
//    }];
}

- (NSDictionary *)constantsToExport
{
    return @{ @"UNKNOWN": @0,  @"DISABLED": @1, @"ENABLED": @2};
}

@end
  
