/*
 * Copyright (c) 2003-2021 The Apereo Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://opensource.org/licenses/ecl2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.batchprocessing;

import java.time.Instant;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Topic {

    private String id;

    private String siteId;

    private String aboutReference = "";

    private String title;

    private String message = "";

    private String type = "QUESTION";

    private Boolean resolved = Boolean.FALSE;

    private Integer howActive = 0;

    private Instant lastActivity;

    private Timestamp lastActivity1;

    private Boolean pinned = Boolean.FALSE;

    private Boolean draft = Boolean.FALSE;

    private Boolean hidden = Boolean.FALSE;

    private Boolean locked = Boolean.FALSE;

    private Boolean anonymous = Boolean.FALSE;

    private Boolean allowAnonymousPosts = Boolean.FALSE;

    private String visibility = "SITE";

    private String creator;

    private Instant created;

    private Timestamp created1;

    private String modifier;

    private Instant modified;

    private Timestamp modified1;
}
