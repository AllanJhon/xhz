/*
 * Copyright (c) 2015, 张涛.
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
package com.sjz.zyl.appdemo;

/**
 * 配置文件常量
 *
 * @author by 张迎乐(yingle1991@gmail.com)
 * @since 2018-01-25.
 */
public class AppConfig {
    public static final String saveFolder = "WisdomVillage";
    public static final String httpCachePath = saveFolder + "/httpCache";
    public static final String audioPath = saveFolder + "/audio";

    public static final String root_path="http://47.104.86.18/";
    public static final String image_path="http://sj-xhz.oss-cn-qingdao.aliyuncs.com/";
    public static final String CACHE_TIME_KEY = "cache_time_key";

    public static final String SPLASH_HEAD_IMG_KEY = "headimage_key";
    public static final String SPLASH_BACKGROUND_KEY = "main_background_key";
    public static final String SPLASH_BOX_KEY = "main_box_key";
    public static final String SPLASH_CONTENT_KEY = "main_content_key";

    public static final String PUSH_SWITCH_FILE = "push_switch_file";
    public static final String PUSH_SWITCH_KEY = "push_switch_key";

    public static final String PUSH_BROADCAST_ACTION = "com.sjz.WisdomVillage.Wvpush_has_new_message";
}
