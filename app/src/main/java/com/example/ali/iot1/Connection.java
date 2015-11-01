/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.example.ali.iot1;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a {@link org.eclipse.paho.android.service.MqttAndroidClient} and the actions it has performed
 */
public class Connection {

  /*
   * Basic Information about the client
   */
    /**
     * ClientHandle for this Connection Object*
     */
    private String clientHandle = null;
    /**
     * The clientId of the client associated with this <code>Connection</code> object *
     */
    private String clientId = null;
    /**
     * The host that the {@link org.eclipse.paho.android.service.MqttAndroidClient} represented by this <code>Connection</code> is represented by *
     */
    private String host = null;
    /**
     * The port on the server this client is connecting to *
     */
    private int port = 0;
    /**
     * {@link ConnectionStatus} of the {@link org.eclipse.paho.android.service.MqttAndroidClient} represented by this <code>Connection</code> object. Default value is {@link ConnectionStatus#NONE} *
     */
    private ConnectionStatus status = ConnectionStatus.NONE;

    /**
     * The {@link org.eclipse.paho.android.service.MqttAndroidClient} instance this class represents*
     */
    private MqttAndroidClient client = null;

    /**
     * Collection of {@link java.beans.PropertyChangeListener} *
     */
//    private ArrayList<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

    /**
     * The {@link android.content.Context} of the application this object is part of*
     */
    private Context context = null;

    /**
     * The {@link org.eclipse.paho.client.mqttv3.MqttConnectOptions} that were used to connect this client*
     */
    private MqttConnectOptions conOpt;

    /**
     * True if this connection is secured using SSL *
     */
    private boolean sslConnection = false;

    public static Connection getInstance() {
        return instance;
    }


    /**
     * Connections status for  a connection
     */
    enum ConnectionStatus {

        /**
         * Client is Connecting *
         */
        CONNECTING,
        /**
         * Client is Connected *
         */
        CONNECTED,
        /**
         * Client is Disconnecting *
         */
        DISCONNECTING,
        /**
         * Client is Disconnected *
         */
        DISCONNECTED,
        /**
         * Client has encountered an Error *
         */
        ERROR,
        /**
         * Status is unknown *
         */
        NONE
    }


    /**
     * Connection instance that handles the mqtt connection
     */
    private static Connection instance = null;


    /**
     * Creates a connection from persisted information in the database store, attempting
     * to create a {@link org.eclipse.paho.android.service.MqttAndroidClient} and the client handle.
     *
     * @param clientId      The id of the client
     * @param context       the application context
     * @param sslConnection true if the connection is secured by SSL
     * @return a new instance of <code>Connection</code>
     */
    public static Connection createConnection(String clientId,
                                              Context context, boolean sslConnection) {
        if (instance != null) {
            return instance;
        }
        String handle = null;
        String uri = null;
        String host = context.getResources().getString(R.string.mqtt_broker_url);
        int port = Integer.parseInt(context.getResources().getString(R.string.mqtt_broker_port));
        if (sslConnection) {
            uri = "ssl://" + host + ":" + port;
            handle = uri + clientId;
        } else {
            uri = "tcp://" + host + ":" + port;
            handle = uri + clientId;
        }
        MqttAndroidClient client = new MqttAndroidClient(context, uri, clientId);
        instance = new Connection(handle, clientId, host, port, context, client, sslConnection);

        return instance;

    }

    /**
     * Creates a connection object with the server information and the client
     * hand which is the reference used to pass the client around activities
     *
     * @param clientHandle  The handle to this <code>Connection</code> object
     * @param clientId      The Id of the client
     * @param host          The server which the client is connecting to
     * @param port          The port on the server which the client will attempt to connect to
     * @param context       The application context
     * @param client        The MqttAndroidClient which communicates with the service for this connection
     * @param sslConnection true if the connection is secured by SSL
     */
    private Connection(String clientHandle, String clientId, String host,
                       int port, Context context, MqttAndroidClient client, boolean sslConnection) {
        //generate the client handle from its hash code
        this.clientHandle = clientHandle;
        this.clientId = clientId;
        this.host = host;
        this.port = port;
        this.context = context;
        this.client = client;
        this.sslConnection = sslConnection;


    }


    /**
     * Process data from the connect action
     */
    public void connect() {
        MqttConnectOptions conOpt = new MqttConnectOptions();
    /*
     * Mutal Auth connections could do something like this
     *
     *
     * SSLContext context = SSLContext.getDefault();
     * context.init({new CustomX509KeyManager()},null,null); //where CustomX509KeyManager proxies calls to keychain api
     * SSLSocketFactory factory = context.getSSLSocketFactory();
     *
     * MqttConnectOptions options = new MqttConnectOptions();
     * options.setSocketFactory(factory);
     *
     * client.connect(options);
     *
     */

        /**
         *if ssl connection configured
         *
         */
        /**
         if (isSSL() == 1) {
         try {
         if (ssl_key != null && !ssl_key.equalsIgnoreCase("")) {
         FileInputStream key = new FileInputStream(ssl_key);
         conOpt.setSocketFactory(client.getSSLSocketFactory(key,
         "mqtttest"));
         }

         } catch (MqttSecurityException e) {
         Log.e(this.getClass().getCanonicalName(),
         "MqttException Occured: ", e);
         } catch (FileNotFoundException e) {
         Log.e(this.getClass().getCanonicalName(),
         "MqttException Occured: SSL Key file not found", e);
         }
         }
         */
        // create a client handle
        String clientHandle = getHostName() + clientId;

        // last will message


//        this.registerChangeListener(changeListener);
        // connect client

        String[] actionArgs = new String[1];
        actionArgs[0] = clientId;
        this.changeConnectionStatus(ConnectionStatus.CONNECTING);

        conOpt.setCleanSession(true);
//        conOpt.setConnectionTimeout(timeout);
//        conOpt.setKeepAliveInterval(keepalive);
//        if (!username.equals(ActivityConstants.empty)) {
//            conOpt.setUserName(username);
//        }
//        if (!password.equals(ActivityConstants.empty)) {
//            conOpt.setPassword(password.toCharArray());
//        }

        final ActionListener callback = new ActionListener(this.context,
                ActionListener.Action.CONNECT, clientHandle, actionArgs);

        boolean doConnect = true;

//        if ((!message.equals(ActivityConstants.empty))
//                || (!topic.equals(ActivityConstants.empty))) {
//            // need to make a message since last will is set
//            try {
//                conOpt.setWill(topic, message.getBytes(), qos.intValue(),
//                        retained.booleanValue());
//            } catch (Exception e) {
//                Log.e(this.getClass().getCanonicalName(), "Exception Occured", e);
//                doConnect = false;
//                callback.onFailure(null, e);
//            }
//        }
        client.setCallback(new MqttCallbackHandler(this.context, clientHandle));


        //set traceCallback
        client.setTraceCallback(new MqttTraceCallback());

        this.addConnectionOptions(conOpt);
        if (doConnect) {
            try {


                client.connect(conOpt, null, callback);


            } catch (MqttException e) {
                Log.e(this.getClass().getCanonicalName(),
                        "MqttException Occured", e);
            }
        }



        try {
            String topic= ActivityConstants.locationTopic;
            String[] topics = new String[1];
            topics[0] = topic;
            client.subscribe(topic, 1);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets the client handle for this connection
     *
     * @return client Handle for this connection
     */
    public String handle() {
        return clientHandle;
    }

    /**
     * Determines if the client is connected
     *
     * @return is the client connected
     */
    public boolean isConnected() {
        return status == ConnectionStatus.CONNECTED;
    }

    /**
     * Changes the connection status of the client
     *
     * @param connectionStatus The connection status of this connection
     */
    public void changeConnectionStatus(ConnectionStatus connectionStatus) {
        status = connectionStatus;
//        notifyListeners((new PropertyChangeEvent(this, ActivityConstants.ConnectionStatusProperty, null, null)));
    }

    /**
     * A string representing the state of the client this connection
     * object represents
     *
     * @return A string representing the state of the client
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(clientId);
        sb.append("\n ");

        switch (status) {

            case CONNECTED:
                sb.append(context.getString(R.string.connectedto));
                break;
            case DISCONNECTED:
                sb.append(context.getString(R.string.disconnected));
                break;
            case NONE:
                sb.append(context.getString(R.string.no_status));
                break;
            case CONNECTING:
                sb.append(context.getString(R.string.connecting));
                break;
            case DISCONNECTING:
                sb.append(context.getString(R.string.disconnecting));
                break;
            case ERROR:
                sb.append(context.getString(R.string.connectionError));
        }
        sb.append(" ");
        sb.append(host);

        return sb.toString();
    }

    /**
     * Determines if a given handle refers to this client
     *
     * @param handle The handle to compare with this clients handle
     * @return true if the handles match
     */
    public boolean isHandle(String handle) {
        return clientHandle.equals(handle);
    }

    /**
     * Compares two connection objects for equality
     * this only takes account of the client handle
     *
     * @param o The object to compare to
     * @return true if the client handles match
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Connection)) {
            return false;
        }

        Connection c = (Connection) o;

        return clientHandle.equals(c.clientHandle);

    }

    /**
     * Get the client Id for the client this object represents
     *
     * @return the client id for the client this object represents
     */
    public String getId() {
        return clientId;
    }

    /**
     * Get the host name of the server that this connection object is associated with
     *
     * @return the host name of the server this connection object is associated with
     */
    public String getHostName() {

        return host;
    }

    /**
     * Determines if the client is in a state of connecting or connected.
     *
     * @return if the client is connecting or connected
     */
    public boolean isConnectedOrConnecting() {
        return (status == ConnectionStatus.CONNECTED) || (status == ConnectionStatus.CONNECTING);
    }

    /**
     * Client is currently not in an error state
     *
     * @return true if the client is in not an error state
     */
    public boolean noError() {
        return status != ConnectionStatus.ERROR;
    }

    /**
     * Gets the client which communicates with the android service.
     *
     * @return the client which communicates with the android service
     */
    public MqttAndroidClient getClient() {
        return client;
    }

    /**
     * Add the connectOptions used to connect the client to the server
     *
     * @param connectOptions the connectOptions used to connect to the server
     */
    public void addConnectionOptions(MqttConnectOptions connectOptions) {
        conOpt = connectOptions;

    }

    /**
     * Get the connectOptions used to connect this client to the server
     *
     * @return The connectOptions used to connect the client to the server
     */
    public MqttConnectOptions getConnectionOptions() {
        return conOpt;
    }

    /**
     * Register a {@link java.beans.PropertyChangeListener} to this object
     *
     * @param listener the listener to register
     */
//    public void registerChangeListener(PropertyChangeListener listener) {
//        listeners.add(listener);
//    }

    /**
     * Remove a registered {@link java.beans.PropertyChangeListener}
     *
     * @param listener A reference to the listener to remove
     */
//    public void removeChangeListener(PropertyChangeListener listener) {
//        if (listener != null) {
//            listeners.remove(listener);
//        }
//    }

    /**
     * Notify {@link java.beans.PropertyChangeListener} objects that the object has been updated
     *
     * @param propertyChangeEvent
     */
//    private void notifyListeners(PropertyChangeEvent propertyChangeEvent) {
//        for (PropertyChangeListener listener : listeners) {
//            listener.propertyChange(propertyChangeEvent);
//        }
//    }

    /**
     * Gets the port that this connection connects to.
     *
     * @return port that this connection connects to
     */
    public int getPort() {
        return port;
    }

    /**
     * Determines if the connection is secured using SSL, returning a C style integer value
     *
     * @return 1 if SSL secured 0 if plain text
     */
    public int isSSL() {
        return sslConnection ? 1 : 0;
    }


}
