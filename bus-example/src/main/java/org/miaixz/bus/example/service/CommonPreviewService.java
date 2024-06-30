/*
 * Copyright 2004 - 2012 Mirko Nasato and contributors
 *           2016 - 2019 Simon Braconnier and contributors
 *
 * This file is part of JODConverter - Java OpenDocument Converter.
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
package org.miaixz.bus.example.service;

import org.miaixz.bus.base.service.BaseService;
import org.miaixz.bus.example.entity.CommonVersion;

import java.io.InputStream;
import java.io.OutputStream;

public interface CommonPreviewService extends BaseService<CommonVersion> {

    /**
     * 文件预览
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param type         转换类型
     * @param filename     文件名
     */
    void preview(InputStream inputStream, OutputStream outputStream, String type, String filename);

}
