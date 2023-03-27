# keycloak-filter-provider-users

This extension lets an administrator automatically disable users that log in via an external identity provider, unless they belong to a list.
The allowed users can be filtered by username or emails. This way, for example, **only** some predefined users from Facebook can access
your application instead of anyone with a Facebook account.

This is intended to be very simple and straightforward. For anything more elaborate you may want to consider [this extension instead](https://github.com/sventorben/keycloak-restrict-client-auth).

This was adapted from [this article](https://developers.redhat.com/blog/2020/12/30/how-to-restrict-users-being-authenticated-in-keycloak-during-identity-brokering): it fixes bugs and implements
the allow list as user input rather than a file.

# Build

```
mvn clean install
```

# Deploy

Copy the built jar into {KEYCLOAK_HOME}/standalone/deployments (for Wildfly) or /opt/keycloak/providers (for Quarkus).

You can also build a custom container image that includes the extension by using the Dockerfile in the repo:

```
podman build -t custom_kc -f Dockerfile
```

# Configure

1. Click on "Authentication" for your realm.
2. Duplicate the "First broker login" flow and edit the copy.
3. Add a step: pick "Enable User if Username in allow list"
4. Move that step below "Create User if Unique"
5. Change step requirement to "Required"
6. Click on the cog to configure your allow list with comma-separated values
7. Pick your external identity provider
8. In "Advanced Settings" choose your duplicated flow as the first login flow.

See the GIF below for a visual demo.

![image](./idp_allow_list.gif)