package com.api.quiz.model;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.util.Arrays;

public enum UserRole {
    STUDENT("STUDENT", 0 ), 
    TEACHER( "TEACHER", 1 ),
    USER ( "USER", 2 ),
    ADMIN("ADMIN", 3),
    GUEST("GUEST", 4);

    private final String role;
    private final int value;

    UserRole(String role, int value) {
        this.role = role;
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public String getRole() {
        return this.role;
    }

    public String toString() {
        return this.value + " " + this.name();
    }

    public static UserRole valueOf( int code ) {
        UserRole status = resolve(code);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        } else {
            return status;
        }
    }

    @Nullable
    public static UserRole resolve( int code ) {
        UserRole[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            UserRole userRole = var1[var3];
            if (userRole.value == code) {
                return userRole;
            }
        }
        return null;
    }

}
