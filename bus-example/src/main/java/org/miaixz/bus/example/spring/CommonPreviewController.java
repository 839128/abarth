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
package org.miaixz.bus.example.spring;

import org.miaixz.bus.base.spring.BaseController;
import org.miaixz.bus.example.entity.CommonVersion;
import org.miaixz.bus.example.service.CommonPreviewService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 版本管理
 */
@RestController
@RequestMapping("/preview")
public class CommonPreviewController extends BaseController<CommonPreviewService, CommonVersion> {

}
