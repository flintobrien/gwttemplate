package com.greenenergycorp.gwttemplate.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.greenenergycorp.gwttemplate.client.gwtp.MyGinjector;
import com.greenenergycorp.gwttemplate.shared.gwtp.SendTextToServer;
import com.greenenergycorp.gwttemplate.shared.gwtp.SendTextToServerResult;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class gwttemplate implements EntryPoint
{
    public final MyGinjector ginjector = GWT.create( MyGinjector.class );

    private final GwtTemplateConstants gwtTemplateConstants = GWT.create( GwtTemplateConstants.class );

    @Override
    public void onModuleLoad()
    {
        RootPanel.get().add( new Label( gwtTemplateConstants.hello() ) );
        RootPanel.get().add( new Label( "Available Locals:" ) );
        RootPanel.get().add( makeLocaleSelector() );

        ginjector.getDispatchAsync().execute( new SendTextToServer( "some text" ), new AsyncCallback<SendTextToServerResult>() {

            @Override
            public void onFailure( Throwable caught )
            {
                Window.alert( caught + "" );
            }

            @Override
            public void onSuccess( SendTextToServerResult result )
            {
                Window.alert( result.getResponse() );
            }
        } );
    }

    private ListBox makeLocaleSelector()
    {
        ListBox localSelector = new ListBox();

        for ( String localeName : LocaleInfo.getAvailableLocaleNames() )
        {
            if ( localeName.equals( "default" ) )
            {
                localSelector.addItem( "English - en", localeName );
            }
            else
            {
                String display = LocaleInfo.getLocaleNativeDisplayName( localeName );
                localSelector.addItem( display + " - " + localeName, localeName );
            }
        }

        return localSelector;
    }
}
