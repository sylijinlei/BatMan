
package com.batman.package1;
/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.google.android.vending.expansion.downloader.impl.DownloaderService;

/**
 * This class demonstrates the minimal client implementation of the DownloaderService from
 * the Downloader library.  Since services must be uniquely registered across all of Android
 * it's a good idea for services to reside directly within your Android application package.
 */
public class SampleDownloaderService extends DownloaderService {
    // stuff for LVL -- MODIFY FOR YOUR APPLICATION!
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArCFOsB90N/bH1A9/5usyzzSInCK8pEDg95d+XvZpik+z+zeDT8T03HzO+4kraq2heeh3bIo6tzcLk0ST3FldtSCBX7GU5n4vucD27K3rmvhx1UztkJaXBm1H5A+56+8sxACaWTnfRDJRw31F7bV4QXqfK9KZy6tcg6nqliW3ybDr1qAAyQtb99TuQL6ze1TH9UB4Yy4DDXByB7f9XaW0XtEgEQLtzvqqlA3SCccvpvEs/HYyIHcj6zT/5/o/sYlwSHIFzyBlxgstOuNYliMTdvz0F0VNn0m4cMVp3QXmig8XO9ZqX1GZ8r8XFlu2WHuzmYAazTH5P2coaX+7J3BzfQIDAQAB";
    // used by the preference obfuscater
    private static final byte[] SALT = new byte[] {
            1, 43, -12, -11, 4, 8,
            -100, -12, 43, 12, -8, -4, 9, 5, -106, -108, -33, 45, -1, 84
    };

    /**
     * This public key comes from your Android Market publisher account, and it
     * used by the LVL to validate responses from Market on your behalf.
     */
    @Override
    public String getPublicKey() {
        return BASE64_PUBLIC_KEY;
    }

    /**
     * This is used by the preference obfuscater to make sure that your
     * obfuscated preferences are different than the ones used by other
     * applications.
     */
    @Override
    public byte[] getSALT() {
        return SALT;
    }

    /**
     * Fill this in with the class name for your alarm receiver. We do this
     * because receivers must be unique across all of Android (it's a good idea
     * to make sure that your receiver is in your unique package)
     */
    @Override
    public String getAlarmReceiverClassName() {
        return SampleAlarmReceiver.class.getName();
    }

}
