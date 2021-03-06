/*
 * Copyright 2010-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.mobileconnectors.amazonmobileanalytics.monetization;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.amazonaws.mobileconnectors.amazonmobileanalytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.MobileAnalyticsTestBase;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.EventClient;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.internal.event.MockInternalEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MonetizationEventBuilderTest extends MobileAnalyticsTestBase {

    static class MonetizationEventBuilderTestImpl extends MonetizationEventBuilder {

        private final boolean isValid;

        public MonetizationEventBuilderTestImpl(EventClient eventClient, boolean mockIsValid) {
            super(eventClient);
            this.isValid = mockIsValid;
        }

        @Override
        protected boolean isValid() {
            return this.isValid;
        }

        @Override
        public String getProductId() {
            return super.getProductId();
        }

        @Override
        public void setProductId(String productId) {
            super.setProductId(productId);
        }

        @Override
        public Double getQuantity() {
            return super.getQuantity();
        }

        @Override
        public void setQuantity(Double quantity) {
            super.setQuantity(quantity);
        }

        @Override
        public Double getItemPrice() {
            return super.getItemPrice();
        }

        @Override
        public void setItemPrice(Double itemPrice) {
            super.setItemPrice(itemPrice);
        }

        @Override
        public String getFormattedItemPrice() {
            return super.getFormattedItemPrice();
        }

        @Override
        public void setFormattedItemPrice(String itemPriceFormatted) {
            super.setFormattedItemPrice(itemPriceFormatted);
        }

        @Override
        public String getCurrency() {
            return super.getCurrency();
        }

        @Override
        public void setCurrency(String currency) {
            super.setCurrency(currency);
        }

        @Override
        public String getStore() {
            return super.getStore();
        }

        @Override
        public void setStore(String store) {
            super.setStore(store);
        }

        @Override
        public String getTransactionId() {
            return super.getTransactionId();
        }

        @Override
        public void setTransactionId(String transactionId) {
            super.setTransactionId(transactionId);
        }
    }

    EventClient mockEventClient = Mockito.mock(EventClient.class);

    @Before
    public void setup() {
        AnalyticsEvent testEvent = MockInternalEvent.newInstance(
                MonetizationEventBuilder.PURCHASE_EVENT_NAME, 0l);
        when(mockEventClient.createEvent(MonetizationEventBuilder.PURCHASE_EVENT_NAME)).thenReturn(
                testEvent);
    }

    @Test
    public void build_productIdNotSet_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, null, "$.99", "123abc", "Amazon", "USD", 1.0, .99);
    }

    @Test
    public void build_productIdEmpty_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "", "$.99", "123abc", "Amazon", "USD", 1.0, .99);
    }

    @Test
    public void build_quantityNotSet_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", ".99", "123abc", "Amazon",
                "USD", null, .99);

    }

    @Test
    public void build_storeNotSet_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", "$.99", "123abc", null, "USD",
                1.0, .99);
    }

    @Test
    public void build_storeEmpty_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", "$.99", "123abc", "", "USD",
                1.0, .99);
    }

    @Test
    public void build_eventClientNotSet_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(null, true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", "$.99", "123abc", "Amazon",
                "USD", 1.0, .99);
    }

    @Test
    public void build_priceSetButCurrencyNotSet_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", null, "123abc", "Amazon", null,
                1.0, .99);
    }

    @Test
    public void build_priceSetButCurrencyEmpty_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", null, "123abc", "Amazon", "",
                1.0, .99);
    }

    @Test
    public void build_currencySetButPriceNotSet_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", null, "123abc", "Amazon", "USD",
                1.0, null);
    }

    @Test
    public void build_emptyFormattedPriceSet_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", "", "123abc", "Amazon", null,
                1.0, null);
    }

    @Test
    public void build_emptyCurrencyAndFormattedPrice_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, false, "com.amazon.item", "", "123abc", "Amazon", "", 1.0,
                null);
    }

    @Test
    public void build_onlyFormattedPriceSet_returnsEvent() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, true, "com.amazon.item", "$.99", "123abc", "Amazon", null,
                1.0, null);
    }

    @Test
    public void build_currencySetAndLocalPriceSet_returnsEvent() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, true, "com.amazon.item", null, "123abc", "Amazon", "USD",
                1.0, .99);
    }

    @Test
    public void build_priceAndFormattedPriceSet__noCurrency_returnsEvent() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, true, "com.amazon.item", "$.99", "123abc", "Amazon", null,
                1.0, .99);
    }

    @Test
    public void build_noTransactionId_returnsEvent() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, true, "com.amazon.item", "$.99", null, "Amazon", "USD",
                1.0, .99);
    }

    @Test
    public void build_allValuesSet_returnsEvent() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                true);
        verifyMonetizationEvent(builder, true, "com.amazon.item", "$.99", "123abc", "Amazon",
                "USD", 1.0, .99);
    }

    @Test
    public void build_derivedClassIsInvalid_returnsNull() {
        MonetizationEventBuilder builder = new MonetizationEventBuilderTestImpl(mockEventClient,
                false);
        verifyMonetizationEvent(builder, false, "com.amazon.item", "$.99", "123abc", "Amazon",
                "USD", 1.0, .99);
    }

    private static void verifyMonetizationEvent(MonetizationEventBuilder builder,
            boolean successfulBuild, String productId, String formattedPrice,
            String transactionId, String store, String currency, Double quantity, Double itemPrice) {

        if (productId != null) {
            builder.setProductId(productId);
        }
        if (formattedPrice != null) {
            builder.setFormattedItemPrice(formattedPrice);
        }
        if (transactionId != null) {
            builder.setTransactionId(transactionId);
        }
        if (store != null) {
            builder.setStore(store);
        }
        if (currency != null) {
            builder.setCurrency(currency);
        }
        if (quantity != null) {
            builder.setQuantity(quantity);
        }
        if (itemPrice != null) {
            builder.setItemPrice(itemPrice);
        }

        AnalyticsEvent purchaseEvent = builder.build();

        if (successfulBuild) {
            assertThat(purchaseEvent, is(not(nullValue())));
            assertThat(purchaseEvent.getEventType(),
                    is(MonetizationEventBuilder.PURCHASE_EVENT_NAME));

            Map<String, String> attributes = purchaseEvent.getAllAttributes();
            assertThat(attributes.get(MonetizationEventBuilder.PURCHASE_EVENT_PRODUCT_ID_ATTR),
                    is(productId));
            assertThat(
                    attributes.get(MonetizationEventBuilder.PURCHASE_EVENT_PRICE_FORMATTED_ATTR),
                    is(formattedPrice));
            assertThat(attributes.get(MonetizationEventBuilder.PURCHASE_EVENT_TRANSACTION_ID_ATTR),
                    is(transactionId));
            assertThat(attributes.get(MonetizationEventBuilder.PURCHASE_EVENT_STORE_ATTR),
                    is(store));
            assertThat(attributes.get(MonetizationEventBuilder.PURCHASE_EVENT_CURRENCY_ATTR),
                    is(currency));

            Map<String, Double> metrics = purchaseEvent.getAllMetrics();
            assertThat(metrics.get(MonetizationEventBuilder.PURCHASE_EVENT_QUANTITY_METRIC)
                    .doubleValue(), is(quantity));
            if (metrics.get(MonetizationEventBuilder.PURCHASE_EVENT_ITEM_PRICE_METRIC) == null) {
                assertThat(metrics.get(MonetizationEventBuilder.PURCHASE_EVENT_ITEM_PRICE_METRIC),
                        is(nullValue()));
            } else {
                assertThat(metrics.get(MonetizationEventBuilder.PURCHASE_EVENT_ITEM_PRICE_METRIC)
                        .doubleValue(), is(itemPrice));
            }

        } else {
            assertThat(purchaseEvent, is(nullValue()));
        }
    }

}
