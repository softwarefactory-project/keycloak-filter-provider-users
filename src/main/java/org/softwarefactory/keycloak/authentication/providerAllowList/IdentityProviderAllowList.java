/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
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

package org.softwarefactory.keycloak.authentication.providerAllowList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.broker.IdpCreateUserIfUniqueAuthenticator;
import org.keycloak.authentication.authenticators.broker.util.SerializedBrokeredIdentityContext;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.models.UserModel;

/**
 *
 * @author mhuin@redhat.com
 */
public class IdentityProviderAllowList extends IdpCreateUserIfUniqueAuthenticator {
    
    @Override
    protected void userRegisteredSuccess(AuthenticationFlowContext context, UserModel registeredUser, SerializedBrokeredIdentityContext serializedCtx, BrokeredIdentityContext brokerContext) {
        Logger.getLogger(IdentityProviderAllowList.class.getName()).log(Level.FINE, null, "User "+registeredUser.getUsername()+" is successfully registered, checking allowlist ...");

        List users = new ArrayList();
        if (context.getAuthenticatorConfig() != null) {
            String usersList = context.getAuthenticatorConfig().getConfig().get("users.allowlist");
            Pattern pattern = Pattern.compile(",");
            users = Arrays.asList(pattern.split(usersList));
        }
        if(!users.contains(registeredUser.getUsername()) || !users.contains(registeredUser.getEmail())){
            // Disable the user if not in the list
            registeredUser.setEnabled(false);
            Logger.getLogger(IdentityProviderAllowList.class.getName()).log(Level.FINE, null, registeredUser.getUsername()+" disabled.");
        }
        
    }
}