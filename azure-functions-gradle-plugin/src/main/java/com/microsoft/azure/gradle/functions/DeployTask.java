/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.gradle.functions;

import com.microsoft.azure.gradle.functions.auth.AuthConfiguration;
import com.microsoft.azure.gradle.functions.auth.AzureAuthHelper;
import com.microsoft.azure.management.Azure;
import org.gradle.api.DefaultTask;

public class DeployTask  extends DefaultTask implements AuthConfiguration {
    public static final String FUNCTION_DEPLOY_START = "Starting deploying to Function App ";
    public static final String FUNCTION_DEPLOY_SUCCESS =
            "Successfully deployed Function App at https://%s.azurewebsites.net";
    public static final String FUNCTION_APP_CREATE_START = "Target Function App does not exist. " +
            "Creating a new Function App ...";
    public static final String FUNCTION_APP_CREATED = "Successfully created Function App ";
    public static final String FUNCTION_APP_UPDATE = "Updating Function App...";
    public static final String FUNCTION_APP_UPDATE_DONE = "Successfully updated Function App ";

    public static final String MS_DEPLOY = "msdeploy";
    public static final String FTP = "ftp";

    private Azure azure;
    private AzureFunctionsExtension azureFunctionsExtension;
    private AzureAuthHelper azureAuthHelper;

    public void setAzureFunctionsExtension(AzureFunctionsExtension azureFunctionsExtension) {
        this.azureFunctionsExtension = azureFunctionsExtension;
        azureAuthHelper = new AzureAuthHelper(this);
    }



    @Override
    public String getUserAgent() {
        return getName() + " " + getGroup();
//        return String.format("%s/%s %s:%s %s:%s", this.getName(), this.getGroup()
//                getPluginName(), getPluginVersion(),
//                INSTALLATION_ID_KEY, getInstallationId(),
//                SESSION_ID_KEY, getSessionId());
    }

    @Override
    public String getSubscriptionId() {
        return (String) getProject().getProperties().get("subscriptionId");
    }

    @Override
    public boolean hasAuthenticationSettings() {
        return getProject().getProperties().containsKey(AzureAuthHelper.CLIENT_ID) || azureFunctionsExtension.getAuthFile() != null;
    }

    @Override
    public String getAuthenticationSetting(String key) {
        return (String) getProject().getProperties().get(key);
    }

    @Override
    public String getAuthFile() {
        return azureFunctionsExtension.getAuthFile();
    }

}