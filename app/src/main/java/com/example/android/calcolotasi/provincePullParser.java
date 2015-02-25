package com.example.android.calcolotasi;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innocenzo on 09/02/15.
 */
public class provincePullParser {

    /*
     *  <provID>1</provID>
            <provNome>Trapani</provNome>
            <descrizione>E' una delle città siciliane</descrizione>
            <prezzoBiglietto>17 euro</prezzoBiglietto>
            <immagine>map_Trapani</immagine>
            <link>http://www.labnova.it</link>
      *
      * */

    private static final String LOGTAG = "calcoloTasi";

    private static final String PROVINCIA_ID = "provID";
    private static final String PROVINCIA_NOME = "provNome";
    private static final String PROVINCIA_DESCRIZIONE = "descrizione";
    private static final String PROVINCIA_PRICE = "prezzoBiglietto";
    private static final String PROVINCIA_IMAGE = "immagine";


    private Provincia currentProvincia = null;
    private String currentTag = null;
    List<Provincia> province = new ArrayList<Provincia>();

    public List<Provincia> parseXML(Context context) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            InputStream  stream = context.getResources().openRawResource(R.raw.province);
            xpp.setInput(stream, null);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_TAG) {
                    handleStartTag(xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(xpp.getText());
                }

                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
           Log.d(LOGTAG, e.getMessage());
        } catch (IOException e) {
            Log.d(LOGTAG, e.getMessage());
        } catch (Resources.NotFoundException e) {
            Log.d(LOGTAG, e.getMessage());
        }


        return province;
    }

    private void handleText(String text) {
        String xmlText = text;
        if(currentProvincia != null && currentTag != null) {
            if(currentTag.equals(PROVINCIA_ID)) {
                Integer id = Integer.parseInt(xmlText);
            }
            else if (currentTag.equals(PROVINCIA_NOME)) {
                currentProvincia.setNome(xmlText);
            }
            else if (currentTag.equals(PROVINCIA_DESCRIZIONE)) {
                currentProvincia.setDescrizione(xmlText);
            }
            else if (currentTag.equals(PROVINCIA_IMAGE)) {
                currentProvincia.setImage(xmlText);
            }
            else if (currentTag.equals(PROVINCIA_PRICE)) {
                Integer price = Integer.parseInt(xmlText);
                currentProvincia.setPrice(price);
            }
        }
    }

    private void handleStartTag(String name) {
        if(name.equals("provincia")) {
            currentProvincia = new Provincia();
            province.add(currentProvincia);
        } else {
            currentTag = name;
        }
    }


}
