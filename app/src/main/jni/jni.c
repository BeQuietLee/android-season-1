//
// Created by lei.li on 6/15/16.
//

#include "com_leili_season1_jni_activity_JNIActivity.h"

JNIEXPORT jstring JNICALL Java_com_leili_season1_jni_activity_JNIActivity_say
        (JNIEnv *env, jobject obj) {

    return (*env)->NewStringUTF(env, "Keep Quiet!");
}