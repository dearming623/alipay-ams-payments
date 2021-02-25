# alipay-ams-payments
alipay ams Supports Java or Android project libraries

* Java can invoke methods by importing a JAR library file

<h3>The sample code</h3>
=========

```java
        AMSConfiguration config = AMSConfiguration.builder()
                        .clientId("SANDBOX_xxxxxxxxxxxxxxxxx")
                        .privateKey("your private key")
                        .alipayPublicKey("youre public key")
                        .gatewayUrl("https://open-na.alipay.com")
        //                .gatewayUrl("https://open-sea.alipay.com")
                        .build();

        String agentToken = "agent token";

        //amount
        long amountInCents = 1000L;
        Currency currency = Currency.getInstance("SGD"); //CNY	China Yuan Renminbi
        Amount amount = new Amount(currency, amountInCents);

        String buyerCode = "Please input a payment code of buyer"; //Scan payment code 

        String paymentRequestId = "MQ20200731STN001_" + System.currentTimeMillis();
        String referenceOrderId = "0000000001";

        Order order = new Order();
        order.setOrderAmount(amount.clone());
        order.setOrderDescription("New White Lace Sleeveless");
        order.setReferenceOrderId(referenceOrderId);
        order.setMerchant(
                new Merchant("Some_Mer", "seller231117459", "7011",
                        new Store("Some_store", "store231117459", "7011")));

        ConsumerPresenter.pay(agentToken, paymentRequestId, order, buyerCode, config, new AMSObjectCallback<Payment>() {
            @Override
            public void onSuccess(Payment response) {
                System.out.println();
                System.out.println("============ Response body ============ ");
                System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFaileure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());
            }
        });
