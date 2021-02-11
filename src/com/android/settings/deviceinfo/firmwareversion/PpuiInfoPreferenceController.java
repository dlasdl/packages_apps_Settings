/*
 * Copyright (C) 2020 Wave-OS
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

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

public class PpuiInfoPreferenceController extends AbstractPreferenceController {

    private static final String KEY_PPUI_INFO = "ppui_info";

    private static final String PROP_PPUI_VERSION = "ro.ppui.version";
    private static final String PROP_PPUI_VERSION_CODE = "ro.ppui.version_code";
    private static final String PROP_PPUI_OFFICIAL = "ro.ppui.is_official";
    private static final String PROP_PPUI_MAINTAINER = "ro.ppui.maintainer_name";
    private static final String PROP_PPUI_DEVICE = "ro.ppui.device_name";

    public PpuiInfoPreferenceController(Context context) {
        super(context);
    }

    private String getDeviceName() {
        String device = SystemProperties.get(PROP_PPUI_DEVICE, "");
        if (device.equals("")) {
            device = Build.MANUFACTURER + " " + Build.MODEL;
        }
        return device;
    }

    private String getPpuiVersion() {
        final String version = SystemProperties.get(PROP_PPUI_VERSION,
                this.mContext.getString(R.string.device_info_default));
        final String versionCode = SystemProperties.get(PROP_PPUI_VERSION_CODE,
                this.mContext.getString(R.string.device_info_default));

        return version + " | " + versionCode;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final LayoutPreference PpuiInfoPreference = screen.findPreference(KEY_PPUI_INFO);
        final TextView version = (TextView) PpuiInfoPreference.findViewById(R.id.version_message);
        final TextView device = (TextView) PpuiInfoPreference.findViewById(R.id.device_message);
        final TextView maintainer = (TextView) PpuiInfoPreference.findViewById(R.id.maintainer_message);
        final TextView releaseType = (TextView) PpuiInfoPreference.findViewById(R.id.release_type_message);
        final String ppuiVersion = getPpuiVersion();
        final String ppuiDevice = getDeviceName();
        final String ppuiMaintainer = SystemProperties.get(PROP_PPUI_MAINTAINER, this.mContext.getString(R.string.device_info_default));
        final String ppuiReleaseType = SystemProperties.getBoolean(PROP_PPUI_OFFICIAL, false) ? "OFFICIAL" : "UNOFFICIAL";
        version.setText(ppuiVersion);
        device.setText(ppuiDevice);
        maintainer.setText(ppuiMaintainer);
        releaseType.setText(ppuiReleaseType);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_PPUI_INFO;
    }
}
