/*
 * Copyright 2018 Heiko Scherrer
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
 * An InvalidPasswordException indicates that a password is not confirm with the defined rules.
 *
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 */
public class InvalidPasswordException extends DomainModelException {

    /**
     * Create a new InvalidPasswordException.
     */
    public InvalidPasswordException() {
        super();
    }

    /**
     * Create a new InvalidPasswordException.
     *
     * @param message The message text
     */
    public InvalidPasswordException(String message) {
        super(message);
    }

    /**
     * Create a new InvalidPasswordException.
     *
     * @param cause The root cause of the exception
     */
    public InvalidPasswordException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new InvalidPasswordException.
     *
     * @param message The message text
     * @param cause The root cause of the exception
     */
    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}