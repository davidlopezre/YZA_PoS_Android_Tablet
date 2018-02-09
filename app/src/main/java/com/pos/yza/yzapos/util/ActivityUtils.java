/*
 * Copyright 2016, The Android Open Source Project
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

package com.pos.yza.yzapos.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId,
                                              String tag,
                                              boolean backStack) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (tag.equals("")){
            transaction.add(frameId, fragment);
        }else {
            transaction.add(frameId, fragment, tag);
        }

        if (backStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public static void replaceFragmentInActivity (@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment, int frameId,
                                                  String tag,
                                                  boolean backStack) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(tag.equals("")){
            transaction.replace(frameId, fragment).addToBackStack(null);
        }else {
            transaction.replace(frameId, fragment, tag).addToBackStack(null);
        }

        if (backStack){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    public static void removeFragmentFromActivity(@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }
}
