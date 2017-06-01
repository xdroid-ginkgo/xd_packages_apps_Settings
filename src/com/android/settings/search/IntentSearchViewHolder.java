/*
 * Copyright (C) 2017 The Android Open Source Project
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
 *
 */
package com.android.settings.search;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.SettingsActivity;

/**
 * ViewHolder for intent based search results.
 * The DatabaseResultLoader is the primary use case for this ViewHolder.
 */
public class IntentSearchViewHolder extends SearchViewHolder {

    public IntentSearchViewHolder(View view) {
        super(view);
    }

    @Override
    public int getClickActionMetricName() {
        return MetricsEvent.ACTION_CLICK_SETTINGS_SEARCH_RESULT;
    }

    @Override
    public void onBind(final SearchFragment fragment, final SearchResult result) {
        super.onBind(fragment, result);

        itemView.setOnClickListener(v -> {
            final Intent intent = result.payload.getIntent();
            final ComponentName cn = intent.getComponent();
            String resultName = intent.getStringExtra(SettingsActivity.EXTRA_SHOW_FRAGMENT);
            if (TextUtils.isEmpty(resultName) && cn != null) {
                resultName = cn.flattenToString();
            }
            fragment.onSearchResultClicked(this, resultName);
            fragment.startActivity(intent);
        });
    }
}