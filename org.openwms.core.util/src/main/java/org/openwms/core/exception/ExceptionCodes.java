/*
 * Copyright 2005-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.exception;

/**
 * An ExceptionCodes class defines some, not all, of exception codes used in OpenWMS.org. The code is a descriptive message code, that can
 * be used for translation purpose.
 *
 * @author Heiko Scherrer
 */
public final class ExceptionCodes {

    /** Thrown if multiple entities were found but only one was expected. */
    public static final String MULIPLE_ENTITIES_FOUND = "integration.multiple.entities.found";
    /** Generic technical exception. */
    public static final String TECHNICAL_RT_ERROR = "core.general.technical.error";
    /** Thrown to signal one or more invalid parameters in API or method calls. */
    public static final String INVALID_PARAMETER_ERROR = "core.invalid.parameter";
    /** A generic validation error. */
    public static final String VALIDATION_ERROR = "core.validation.error";

    /** Thrown if an entity to persist already exists. */
    public static final String ENTITY_ALREADY_EXISTS = "entity.already.exists";
    /** Thrown if an entity has been looked up but hasn't been found. */
    public static final String ENTITY_NOT_EXIST = "entity.not.exist";
    /** Thrown if an operation was called with {@code null} argument. */
    public static final String ENTITY_NOT_BE_NULL = "entity.null.argument";

    /** Thrown if an User to persist already exists. */
    public static final String USER_ALREADY_EXISTS = "user.already.exists";
    /** Thrown if an User has been looked up but hasn't been found. */
    public static final String USER_WITH_PKEY_NOT_EXIST = "user.pkey.not.exist";
    /** Thrown if an User has been looked up but hasn't been found. */
    public static final String USER_WITH_NAME_NOT_EXIST = "user.name.not.exist";
    /** Thrown if an User has been looked up but hasn't been found. */
    public static final String USER_NOT_EXIST = "user.not.exist";
    /** Thrown if removing a User was requested with {@code null} argument. */
    public static final String USER_REMOVE_NOT_BE_NULL = "user.remove.null.argument";
    /** Thrown if creating a User was requested with {@code null} argument. */
    public static final String USER_CREATE_NOT_BE_NULL = "user.create.null.argument";
    /** Thrown if saving a User was requested with {@code null} argument. */
    public static final String USER_SAVE_NOT_BE_NULL = "user.save.null.argument";
    /** Thrown if saving a User's profile was requested with {@code null} argument. */
    public static final String USER_PROFILE_SAVE_NOT_BE_NULL = "user.profile.save.null.argument";
    /** Thrown if changing a User's password was requested with {@code null} argument. */
    public static final String USER_PW_SAVE_NOT_BE_NULL = "user.password.save.null.argument";
    /** Thrown if changing a User's password was requested, but the new password does not match the defined password rules. */
    public static final String USER_PW_INVALID = "user.password.invalid";

    /** Thrown if a Role to persist already exists. */
    public static final String ROLE_ALREADY_EXISTS = "role.already.exists";
    /** Thrown if a Role is looked up but hasn't been found. */
    public static final String ROLE_NOT_EXIST = "role.not.exist";
    /** Thrown if removing a Role was requested with {@code null} argument. */
    public static final String ROLE_REMOVE_NOT_BE_NULL = "role.remove.null.argument";
    /** Thrown if creating a Role was requested with {@code null} argument. */
    public static final String ROLE_CREATE_NOT_BE_NULL = "role.create.null.argument";
    /** Thrown if saving a Role was requested with {@code null} argument. */
    public static final String ROLE_SAVE_NOT_BE_NULL = "role.save.null.argument";

    /** Thrown if saving the startup order of Modules was requested with {@code null} argument. */
    public static final String MODULE_SAVE_STARTUP_ORDER_NOT_BE_NULL = "module.save.startup.order.null.argument";
    /** Thrown if saving a Module was requested with {@code null} argument. */
    public static final String MODULE_SAVE_NOT_BE_NULL = "module.save.null.argument";
    /** Thrown if a service method was called with expected modulename but that was {@code null}. */
    public static final String MODULENAME_NOT_NULL = "module.modulename.null.argument";

    /** Thrown if a saving a I18n entity was requested with {@code null} argument. */
    public static final String I18N_SAVE_NOT_BE_NULL = "i18n.save.null.argument";

    public static final String USER_IS_TRANSIENT = "user.is.transient";
    public static final String USER_HAS_CHANGED = "user.has.changed";

    public static final String ROLE_IS_TRANSIENT = "role.is.transient";

    private ExceptionCodes() {
    }
}
