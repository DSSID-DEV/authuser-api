package com.ead.authuser.controllers.common;

import lombok.Data;

@Data
public class ConflictChecked {
    private boolean has;
    private String conflict;
    private String msgLog;
}
