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
import java.util.List;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.broker.IdpCreateUserIfUniqueAuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;

/*
 * @author mhuin@redhat.com
 */
public class IdentityProviderAllowListFactory extends IdpCreateUserIfUniqueAuthenticatorFactory {

    public static final String PROVIDER_ID = "identity-provider-allow-list";
    static IdentityProviderAllowList SINGLETON = new IdentityProviderAllowList();

    public static final String ALLOWLIST = "users.allowlist";

    @Override
    public Authenticator create(KeycloakSession session) {
        return SINGLETON;

    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Enable User if username or email in allowed list";
    }

    @Override
    public String getHelpText() {
        return "Enable User if username or email is present in allowed list";
    }

    private static final List configProperties = new ArrayList();

    static {
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName(ALLOWLIST);
        property.setLabel("Allow List");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Set the list of users to allow, comma separated");
        configProperties.add(property);
    }

    @Override
    public List getConfigProperties() {
        return configProperties;
    }
}