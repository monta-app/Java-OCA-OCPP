package eu.chargetime.ocpp;/*
    ChargeTime.eu - Java-OCA-OCPP
    
    MIT License

    Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
 */

import eu.chargetime.ocpp.feature.Feature;
import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public abstract class FeatureHandler {

    private HashMap<String, CompletableFuture<Confirmation>> promises;
    private ArrayList<Feature> featureList;

    FeatureHandler() {
        this.promises = new HashMap<>();
        this.featureList = new ArrayList<>();
    }

    /**
     * Add {@link Profile} to support a group of features.
     *
     * @param profile supported feature {@link Profile}
     * @see Profile
     */
    public void addFeatureProfile(Profile profile) {
        Feature[] features = profile.getFeatureList();
        Collections.addAll(featureList, features);
    }

    /**
     * Search for supported features added with the addProfile.
     * If no supported feature is found, null is returned
     * <p>
     * Can take multiple inputs:
     * {@link String}, search for the action name of the feature.
     * {@link Request}/{@link Confirmation}, search for a feature that matches.
     * Anything else will return null.
     *
     * @param needle Object supports {@link String}, {@link Request} or {@link Confirmation}
     * @return Instance of the supported Feature
     */
    protected Feature findFeature(Object needle) {
        Feature output = null;

        for (Feature feature : featureList) {
            if (featureContains(feature, needle)) {
                output = feature;
                break;
            }
        }

        return output;
    }

    /**
     * Tries to match {@link Feature} with an {@link Object}.
     * Different outcome based on the type:
     * {@link String}, matches the action name of the feature.
     * {@link Request}, matches the request type of the feature.
     * {@link Confirmation}, matches the confirmation type of the feature.
     * Other wise returns false.
     *
     * @param feature to match
     * @param object  to match with, supports {@link String}, {@link Request} or {@link Confirmation}
     * @return true if the {@link Feature} matches the {@link Object}
     */
    protected boolean featureContains(Feature feature, Object object) {
        boolean contains = false;
        contains |= object instanceof String && feature.getAction().equals(object);
        contains |= object instanceof Request && feature.getRequestType() == object.getClass();
        contains |= object instanceof Confirmation && feature.getConfirmationType() == object.getClass();
        return contains;
    }

    /**
     * Creates call back {@link CompletableFuture} for later use
     *
     * @param uniqueId identification for the {@link Request}
     * @return call back {@link CompletableFuture}
     */
    protected CompletableFuture<Confirmation> createPromise(String uniqueId) {
        CompletableFuture<Confirmation> promise = new CompletableFuture<>();
        promises.put(uniqueId, promise);
        return promise;
    }

    /**
     * Get stored call back {@link CompletableFuture}.
     *
     * @param uniqueId identification for the {@link Request}
     * @return call back {@link CompletableFuture}
     */
    protected CompletableFuture<Confirmation> getPromise(String uniqueId) {
        return promises.get(uniqueId);
    }

    /**
     * Remove stored call back {@link CompletableFuture}.
     *
     * @param uniqueId identification for the {@link Request}
     */
    protected void removePromise(String uniqueId) {
        promises.remove(uniqueId);
    }

}
