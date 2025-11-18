package com.travy.SpringRestAPI.Util.Constraints;

public enum Authority {
    
    READ,
    WRITE,
    UPDATE,
    USER, // can, update, read, delete self object
    ADMIN // can read, update, delete any object
}
