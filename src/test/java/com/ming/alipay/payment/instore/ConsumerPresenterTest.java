package com.ming.alipay.payment.instore;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ming.alipay.api.RestfulPath;
import com.ming.alipay.ams.entity.*;
import com.ming.alipay.ams.response.AMSObjectCallback;
import com.ming.alipay.ams.response.AMSResponse;
import com.ming.alipay.config.AMSConfiguration;
import com.ming.alipay.pojo.dto.CancelPaymentDTO;
import com.ming.alipay.pojo.dto.InquiryPaymentDTO;
import com.ming.alipay.util.DateUtil;
import com.ming.alipay.util.SignatureUtil;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/3/2020 3:36 PM
 */
public class ConsumerPresenterTest {


    @org.junit.jupiter.api.Test
    void testPay() {
        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y07882Y2UPY04144")
                .privateKey("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC9vhrJEIr8O15kSURMZiY+orWVeJ649jp1/SPr0TUQQDMwUNV77jfmpcIk/Lp2ePR9anZHy7Yd3tMPmKczogx74jXtawi+nJTuc29tG/2qMPJYm66yESDwRTpC6LHVw/nGDN2umcHEDSuIzt+HqvqZ6lhefHEVNyLNgVp3LXmxPDlSFRo8ePVx19WgVqbgpP0RDk4cm2sNgnhhG+TXylHn1t9U1ImzPVfjD/BYaws/KObh1IwwtlzxWeAUapNuw25XSNJy11/N5dvl9ZPuOPPC7eadtRY8cTf8gERGl/MbIiXkzmk9qvwoWgMbOX9uEFQgmrdRh47L4LG+TOPhHHFVAgMBAAECggEAbLP+0HYjygGRQE8hS8ux2WyKL0ndVQXr1DDkZXODvP2q1eFZjRia+g64vUv0327IUxmtDdDNUyuXK3Y/RDtwf4DHeG87oUg/oqSLj6zblKdaEB2Q5f8eKcjfafm5+EfATxCL6D26XsFU0ZoDQcxYqdkyK1+LwaXG0Ils9J4e+8QSYnHqQUQk+6xodkWbVZlucBNjQCk63BNodqwx81NFNhqaltp3DPAaquNOQpi3OyNgCWlxiPPcglmLUXug49x4chAT81HhSPskibI7bAxYcc54qyfmwG611HA68hdmY0K1n5OdT52WqgV6A1/lHC0g1ewjegetp4SDs19GPZDneQKBgQDc9Pe9oC3/s2MPhaiVnHQNXOErr90devi4P2VX5Uv4XcY5T0RBYr71XiMsNRoua7J7bcWQghqRObDaajw8mi2LR8MqDnwyPXeIFerZxhL4oZqCQoYkwWJ+8j6M8+m5/zzxaAdkI2wmytSTFhYTlh+u2L3/Go7yKC3QA/oAP5AJVwKBgQDb1c9sfTZW9egf/fl0Cq4st6QlTiA78eygOLMtlvhP2ROoUP5+gqem4uwTleJMVWNViLG6xOUYoF5Zr8B+aEY4Ratj4sTGlpr9lSrcbHgm3JrKQF6zBdPImvDQd3hCgAV9kGRd3lHvuj/LkghLdMJloaqN+yO0qLPGAWCjNLvzMwKBgQDTrpSRxJ2M9Q0YV9J00tiT3MGUGv3ZOnCCA6pRlMk9foljaj9X7QRJChDkT1Nv2Zm6iiXWFe7h5sU52sEuxf6QCwY8ingNobmcVHMUg6C1Ng1Sce0V1XdJa0blvt/SY2a9WA6Mryrz1ynkaCpqRU7BMcCEX6tG75tESCsAheS2LQKBgFTAX1FiDHsGHtGGpo84NuICgoPnVMIHiMRUV6+xzlVqSQvQ/exkDqJmRcFfPXlUShGFu49XrUMLQJSvDzUsmjXDlknR+S7ejS5KPdXw83l3/y0OF/RIwNdL9J9KD9EDbhp0el0+ArS7RzECQbRldTSdWRWXxrI1f+lMSqdhAzzFAoGBANfSr+23UWus5ljTUzHBIh4oxpbsxSLeFc4j8x0aUW90/9gup5Gy05fAoM303COGtpkjg4XWEKIAlXoJzamjXNFqIOzo+5VoS6HRmj+mLCqCk5XOg61g4/DEdAQ6pMD0PvS14BxsPzhTlHJIpt04HEjtYy4cQbhNEhge/fN6lfm/")
//                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs965PDzGbKTwqrD5zB7cMvYeL3x6pKy7r5qFeLh3zCjoPHwCCrn0F52iuMDJ9b9VwimxCDr2HSb5UoScQQCysMF+/cZkGMcsTIN3I7ol89MFMhOf1Zn0G0oyMH8q690mpG/6MHY9IhIjp79hOUpX8u3Ju7gOoGnrlp2x1+5pgnFtaqWazEOCDckpz+ydxhOr1Nnt1i4Dw+nGfy5yZObVStxAznsn04s68/mXtaPA7LLfaS72JvkOCAbUkpoDopuVOLvqc8I75uNDo2UKhr8AKA/sMYLxvAabfQsKIwIWPKBku0K9AOEUc6NHp8E7bJPrBj7b3DNd0Pzg7rvLkRuaCQIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
//                .gatewayUrl("https://open-sea.alipay.com")
                .build();

        String agentToken = "i23ZOAZRnDFzozDxyLwfvxg4LIuJNGT8zad6RMuwED0007C1";

        //amount
        long amountInCents = 1000L;
        Currency currency = Currency.getInstance("SGD"); //CNY	China Yuan Renminbi
        Amount amount = new Amount(currency, amountInCents);

        System.out.println("Please input a payment code of buyer：");
        Scanner sc = new Scanner(System.in);
        String buyerCode = sc.nextLine();
//        pay(agentToken,"MQ20200731STN001_" + System.currentTimeMillis(),buyerCode, amount);

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
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });


        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @org.junit.jupiter.api.Test
    void testISODateTime() {

        String isoDateTime = "2011-12-03T10:15:30+08:00";
        Date date = DateUtil.parseISODateTimeStr(isoDateTime);

        String isoDateTimeStr = DateUtil.getISODateTimeStr(date);
        assertEquals(isoDateTime,isoDateTimeStr);
    }

    @org.junit.jupiter.api.Test
    void testDateTime() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        ZonedDateTime now = ZonedDateTime
//                .now()
//                .withNano(0);
//        System.out.println("当前时间： " + now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        String iso8601DateTimeStr = DateUtil.getISO8601DateTimeStr(ZonedDateTime.now());
        ZonedDateTime zdt = DateUtil.parseISO8601DateTimeStr(iso8601DateTimeStr);
        assertEquals(iso8601DateTimeStr, zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        ZonedDateTime zonedDateTime = DateUtil.parseISO8601DateTimeStr("2011-12-03T10:15:30+08:00");
        assertEquals("2011-12-03T10:15:30+08:00", zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @org.junit.jupiter.api.Test
    void testSign() {
        String clientId = "SANDBOX_5Y07882Y2UPY04144";
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCjQIa+O6xT3Ak+dZSH6Gg/O9poiykd1pXmYD9LjS4I2dRr5KEP8br0fiF7hr++1TW4+ahd/peiWQuBehuRIGbODP3n+awdaoA/m2zpU2m5YlBuPMG6TAp0b03qj12SPX3XnxLllpAnOi/ATEo9x9wD66COXnToMJHbiGbGK9dt9ZWUta4Vp79qa42T0301J37px8/jrR4nznplrRyW6Tu3Q6LC86cCP4QkoHGP/kr3LMBU1iBfvZelM27DcAd2ENG8vWqyfyEWgiecP3m4Xfm4C/9WY6QDBLxttk+iBYYDzj4v8Qf/foCOEh8ViSeoQmT3Yu1dPPCbuqOWF7kEMJszAgMBAAECggEALzllQPZmHUQTcHv24XG7Zj9cKM3IsRb3+dJxvNs0K99HcYaLiN82Y69w5BjQstVeWwntSHNzTcVQbL3z54Fl+8kKUeaJgWDjhILjeINmm/fyqFDvAYMpqxDfizC5sqoTEyKzBrMNNCvC0G/BMin0XTX2FfkA/IHAT68fe8gG44mmJDC4mLUcqnZ8lZxEWqCiwAcZlcez7toIIDFA/E7pvE/dBF06S65ftZTDOfL+cR17qOSkH3HNvbdQpJvBq0ea2vR6NXEhrqmlesP8tntuUidfxpXpAwVaMCaAGj/gvHvoRyCeFAeuWGiPenMJ3ABSGZcBumtMdQK3fRx/qkZKIQKBgQDkLZS6M/O+EbcBYvfWIPCRFQA0YUwPNCrpZMEZmUIsHLMTz+33L413Al8mJQFhP4JKZR+mz/knBd+z4zpYj3vV2Tdv9qVpG09OrwiJ4O2rxgNQPi2BeURQ33Q4p5njanJyX7vYKcTxHBFSoIdfzQh3PPn8zyLHrWB/LFvQWIbnawKBgQC3KFNdPnMI4yDANIT8yY2892ehKzyoINlnwsKRIous2XFLE9Ueg0I5J+uQMgADdCYyVSW0B95nm8ZewXhjDQXYO1v+pqG+rojc6wjDB85wtYrOEn1+NuqCAVpdnlqGv/hRno6CjHwkJSD3MhOS/U6InWqYp6MS6oc8v6uxs2M1WQKBgQCjdROqIDVn34Dd+WvZdVdgV08CNwEpkxF6iLbcA7Inr5eJhdbO0L3il0xaIwBODhTj/nUiJrIxhj+uugS+FGt90lGoX2Q3W10A3NqdCwi3mO4euawXqCuMU8cKHuZax5Kc5H/IZoOyxYyTSHQx6Ms7v+7FcA1WRZlvAPP02LYraQKBgFWB2gl6EjJf9vZEjqGFZ0qFJjgz+0lyrIyofDVNr2+uxBmM1L4+ATi1zl+vOBpkq0BsSAHYephcPSwXnqB/f+8HJ1ena3Od//9DKwACMPqlhnvWXggCk1DGWO3D8/dcwA06vMVt2Lb3LoC5PDIvV+6nrxA9kwBNxX0y7nEzkmlhAoGBAJ6ceKPNCfrwc+IGrmUf0Ny5ly2XP+OEE8PL4nPCFOqG+YA4vlmngsQ7KhbmOXCg8lnQtZj70sbj48kqLLxEuVwqpdI/5ViqXdHaMMWk1Rr9BYMTL4nrS9LnvU2tzYOx7faJRhtkIv57SLI709auIxH1dJMglo9rJXpFIc84r8jA";
        String agentToken = "i23ZOAZRnDFzozDxyLwfvxg4LIuJNGT8zad6RMuwED0007C1";

        String requestTime = "2020-08-08T01:53:30+08:00";
        String requestBody = "Ming";
        String sign = SignatureUtil.sign(RestfulPath.API_PAY,
                clientId,
                requestTime,
                privateKey,
                requestBody);

        assertEquals("FgR1htRft4j%2BN%2B3ihWdqVwUzZXTxI5h6gHkHhAWntTtDcQB9kfeOeWdqNA8sWAxR7%2FTCs8bczLgeIZLBhLflcOXgUL%2BRPl%2Bsgt00QpFCckoDvhIIygCwwEgMTESYlr3QooBA2tVMRZUraaSFdipZGuw65ab0l%2FbWyK2x9XNtjFUOiEUMQyllo3ziAUFeb%2BfmAoUYBpetluCwHz45O2oGTF0zNt8adKPrGHajktSr9ALDeUI3k1CJb%2FUF3DF%2FkDnbLpsNsWodYoAVugxe%2BHw5SvwcLTRuKLrqrCMPrPErPyZuUAKPQZyXZetKtU9DuSBYq8S%2BdtaS40rYRhraS6vqmA%3D%3D", sign);
    }

    @org.junit.jupiter.api.Test
    void testConsult() {

        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y07882Y2UPY04144")
                .privateKey("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCjQIa+O6xT3Ak+dZSH6Gg/O9poiykd1pXmYD9LjS4I2dRr5KEP8br0fiF7hr++1TW4+ahd/peiWQuBehuRIGbODP3n+awdaoA/m2zpU2m5YlBuPMG6TAp0b03qj12SPX3XnxLllpAnOi/ATEo9x9wD66COXnToMJHbiGbGK9dt9ZWUta4Vp79qa42T0301J37px8/jrR4nznplrRyW6Tu3Q6LC86cCP4QkoHGP/kr3LMBU1iBfvZelM27DcAd2ENG8vWqyfyEWgiecP3m4Xfm4C/9WY6QDBLxttk+iBYYDzj4v8Qf/foCOEh8ViSeoQmT3Yu1dPPCbuqOWF7kEMJszAgMBAAECggEALzllQPZmHUQTcHv24XG7Zj9cKM3IsRb3+dJxvNs0K99HcYaLiN82Y69w5BjQstVeWwntSHNzTcVQbL3z54Fl+8kKUeaJgWDjhILjeINmm/fyqFDvAYMpqxDfizC5sqoTEyKzBrMNNCvC0G/BMin0XTX2FfkA/IHAT68fe8gG44mmJDC4mLUcqnZ8lZxEWqCiwAcZlcez7toIIDFA/E7pvE/dBF06S65ftZTDOfL+cR17qOSkH3HNvbdQpJvBq0ea2vR6NXEhrqmlesP8tntuUidfxpXpAwVaMCaAGj/gvHvoRyCeFAeuWGiPenMJ3ABSGZcBumtMdQK3fRx/qkZKIQKBgQDkLZS6M/O+EbcBYvfWIPCRFQA0YUwPNCrpZMEZmUIsHLMTz+33L413Al8mJQFhP4JKZR+mz/knBd+z4zpYj3vV2Tdv9qVpG09OrwiJ4O2rxgNQPi2BeURQ33Q4p5njanJyX7vYKcTxHBFSoIdfzQh3PPn8zyLHrWB/LFvQWIbnawKBgQC3KFNdPnMI4yDANIT8yY2892ehKzyoINlnwsKRIous2XFLE9Ueg0I5J+uQMgADdCYyVSW0B95nm8ZewXhjDQXYO1v+pqG+rojc6wjDB85wtYrOEn1+NuqCAVpdnlqGv/hRno6CjHwkJSD3MhOS/U6InWqYp6MS6oc8v6uxs2M1WQKBgQCjdROqIDVn34Dd+WvZdVdgV08CNwEpkxF6iLbcA7Inr5eJhdbO0L3il0xaIwBODhTj/nUiJrIxhj+uugS+FGt90lGoX2Q3W10A3NqdCwi3mO4euawXqCuMU8cKHuZax5Kc5H/IZoOyxYyTSHQx6Ms7v+7FcA1WRZlvAPP02LYraQKBgFWB2gl6EjJf9vZEjqGFZ0qFJjgz+0lyrIyofDVNr2+uxBmM1L4+ATi1zl+vOBpkq0BsSAHYephcPSwXnqB/f+8HJ1ena3Od//9DKwACMPqlhnvWXggCk1DGWO3D8/dcwA06vMVt2Lb3LoC5PDIvV+6nrxA9kwBNxX0y7nEzkmlhAoGBAJ6ceKPNCfrwc+IGrmUf0Ny5ly2XP+OEE8PL4nPCFOqG+YA4vlmngsQ7KhbmOXCg8lnQtZj70sbj48kqLLxEuVwqpdI/5ViqXdHaMMWk1Rr9BYMTL4nrS9LnvU2tzYOx7faJRhtkIv57SLI709auIxH1dJMglo9rJXpFIc84r8jA")
                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs965PDzGbKTwqrD5zB7cMvYeL3x6pKy7r5qFeLh3zCjoPHwCCrn0F52iuMDJ9b9VwimxCDr2HSb5UoScQQCysMF+/cZkGMcsTIN3I7ol89MFMhOf1Zn0G0oyMH8q690mpG/6MHY9IhIjp79hOUpX8u3Ju7gOoGnrlp2x1+5pgnFtaqWazEOCDckpz+ydxhOr1Nnt1i4Dw+nGfy5yZObVStxAznsn04s68/mXtaPA7LLfaS72JvkOCAbUkpoDopuVOLvqc8I75uNDo2UKhr8AKA/sMYLxvAabfQsKIwIWPKBku0K9AOEUc6NHp8E7bJPrBj7b3DNd0Pzg7rvLkRuaCQIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
                .build();

        String authState = UUID.randomUUID().toString();
        ConsumerPresenter.consult(authState, config, new AMSObjectCallback<AMSResponse>() {
            @Override
            public void onSuccess(AMSResponse response) {
                System.out.println();
                System.out.println("============ Response body ============ ");
                System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));


                if (response.isSuccess()) {
                    String authUrl = response.getAuthUrl();
                    System.out.println("authUrl -> " + authUrl);
                }
            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void testQuery() {
        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y07882Y2UPY04144")
                .privateKey("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCjQIa+O6xT3Ak+dZSH6Gg/O9poiykd1pXmYD9LjS4I2dRr5KEP8br0fiF7hr++1TW4+ahd/peiWQuBehuRIGbODP3n+awdaoA/m2zpU2m5YlBuPMG6TAp0b03qj12SPX3XnxLllpAnOi/ATEo9x9wD66COXnToMJHbiGbGK9dt9ZWUta4Vp79qa42T0301J37px8/jrR4nznplrRyW6Tu3Q6LC86cCP4QkoHGP/kr3LMBU1iBfvZelM27DcAd2ENG8vWqyfyEWgiecP3m4Xfm4C/9WY6QDBLxttk+iBYYDzj4v8Qf/foCOEh8ViSeoQmT3Yu1dPPCbuqOWF7kEMJszAgMBAAECggEALzllQPZmHUQTcHv24XG7Zj9cKM3IsRb3+dJxvNs0K99HcYaLiN82Y69w5BjQstVeWwntSHNzTcVQbL3z54Fl+8kKUeaJgWDjhILjeINmm/fyqFDvAYMpqxDfizC5sqoTEyKzBrMNNCvC0G/BMin0XTX2FfkA/IHAT68fe8gG44mmJDC4mLUcqnZ8lZxEWqCiwAcZlcez7toIIDFA/E7pvE/dBF06S65ftZTDOfL+cR17qOSkH3HNvbdQpJvBq0ea2vR6NXEhrqmlesP8tntuUidfxpXpAwVaMCaAGj/gvHvoRyCeFAeuWGiPenMJ3ABSGZcBumtMdQK3fRx/qkZKIQKBgQDkLZS6M/O+EbcBYvfWIPCRFQA0YUwPNCrpZMEZmUIsHLMTz+33L413Al8mJQFhP4JKZR+mz/knBd+z4zpYj3vV2Tdv9qVpG09OrwiJ4O2rxgNQPi2BeURQ33Q4p5njanJyX7vYKcTxHBFSoIdfzQh3PPn8zyLHrWB/LFvQWIbnawKBgQC3KFNdPnMI4yDANIT8yY2892ehKzyoINlnwsKRIous2XFLE9Ueg0I5J+uQMgADdCYyVSW0B95nm8ZewXhjDQXYO1v+pqG+rojc6wjDB85wtYrOEn1+NuqCAVpdnlqGv/hRno6CjHwkJSD3MhOS/U6InWqYp6MS6oc8v6uxs2M1WQKBgQCjdROqIDVn34Dd+WvZdVdgV08CNwEpkxF6iLbcA7Inr5eJhdbO0L3il0xaIwBODhTj/nUiJrIxhj+uugS+FGt90lGoX2Q3W10A3NqdCwi3mO4euawXqCuMU8cKHuZax5Kc5H/IZoOyxYyTSHQx6Ms7v+7FcA1WRZlvAPP02LYraQKBgFWB2gl6EjJf9vZEjqGFZ0qFJjgz+0lyrIyofDVNr2+uxBmM1L4+ATi1zl+vOBpkq0BsSAHYephcPSwXnqB/f+8HJ1ena3Od//9DKwACMPqlhnvWXggCk1DGWO3D8/dcwA06vMVt2Lb3LoC5PDIvV+6nrxA9kwBNxX0y7nEzkmlhAoGBAJ6ceKPNCfrwc+IGrmUf0Ny5ly2XP+OEE8PL4nPCFOqG+YA4vlmngsQ7KhbmOXCg8lnQtZj70sbj48kqLLxEuVwqpdI/5ViqXdHaMMWk1Rr9BYMTL4nrS9LnvU2tzYOx7faJRhtkIv57SLI709auIxH1dJMglo9rJXpFIc84r8jA")
                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs965PDzGbKTwqrD5zB7cMvYeL3x6pKy7r5qFeLh3zCjoPHwCCrn0F52iuMDJ9b9VwimxCDr2HSb5UoScQQCysMF+/cZkGMcsTIN3I7ol89MFMhOf1Zn0G0oyMH8q690mpG/6MHY9IhIjp79hOUpX8u3Ju7gOoGnrlp2x1+5pgnFtaqWazEOCDckpz+ydxhOr1Nnt1i4Dw+nGfy5yZObVStxAznsn04s68/mXtaPA7LLfaS72JvkOCAbUkpoDopuVOLvqc8I75uNDo2UKhr8AKA/sMYLxvAabfQsKIwIWPKBku0K9AOEUc6NHp8E7bJPrBj7b3DNd0Pzg7rvLkRuaCQIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
//                .gatewayUrl("https://open-sea.alipay.com")
                .build();

        String agentToken = "i23ZOAZRnDFzozDxyLwfvxg4LIuJNGT8zad6RMuwED0007C1";

        InquiryPaymentDTO inquiryPayment = new InquiryPaymentDTO();
        inquiryPayment.setPaymentId("202007311140108001001887H0221425716"); // refucnd
//        inquiryPayment.setPaymentId("202008101140108001001885B0221657289"); // pay ok
        ConsumerPresenter.inquiry(agentToken, inquiryPayment, config, new AMSObjectCallback<InquiryPayment>() {
            @Override
            public void onSuccess(InquiryPayment inquiryPayment) {
                System.out.println();
                System.out.println("============ Response body ============ ");
                System.out.println(JSON.toJSONString(inquiryPayment, SerializerFeature.PrettyFormat));
            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });


        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void testCancel() {
        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y08882Y3EDZ03753")
                .privateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBJO3Tvj7VpW+4vMiaGJet6xUZnP40u6U+4q5kC2W3bsy1vQd6odUE0aqKgJ9G7G+qUdVYwYNsA5Srg1a/AE5u3ZAV75avvxpLPr5zg8+rlGBB+9EWAUysMjrkg13odIqn/fVwrXzoA7TKkUT2/4dYxb7YewV6GSC5Z100Q7DFrVE0AFJmmwDG0i3mmT5zSHSBSLQnCaoZAU8HLEKWVds9Rrb8OE8q297ca25L+po/P5A5k/z2pmXMp4mgslt1rIPvqTbxLMQkMDg4OkOjIjcl8IP2SLK930UM3ML+XrHXs+BdASh81y4hQsJecghQ2QQVHobzHYTQstGe9cNXup2rAgMBAAECggEALv/smfIT1hcmSLpT21j14rK6dB7hmPltkyxWQUywkQh+8elAjo3VA7L2eP+EeABWR0sO9QVAOhA3jA/5gvuuj8SMyp7uXTmUbSwBjfK7onsWaQ0nM/X3HHgPXNi+VXoMeEUBjSdmXN30bwBdc/LdJB6bbRegj6YTdcY7cmjsLZR3SE8P5Llnr3K8tnV/v/SrUe19KTCyW7fdVNqeUmiuGrupaE0/rkumY+XvYIYwMQ4HYoX+clKibUzTHrKgkpOMh1c9+QI5Yf9dypkQ5Y6BFecpHhrnDQLzM/QmdznKOCvRiCl9RgTIl72G45GEv5KW6xh2kZZPUDAo5TRqIpW4UQKBgQDSSgJwzqYZyxxvsyLBh2jxeTNofi8UgiC08bxhIWWeWb+P00NSosZQgGDdK/yS1v1ulchHp6wyofrTRTxsmFJC3tnQaCMUQL5Aqa5mSZpTNcYYOB+aXSUxOk6B0vWYJ2Puj1lxTcFazJkj1JF+z4RtkH8qPGEsceCZxb59Za7fQwKBgQCdN3CFfk/EGgFpOS5xnUTEUNtmL114mCF5My33MQHgvStstlF5us5lzaDaBUgifBoR65uy0KKAMIhHbEhQSeWq+6sC4ncx2wsv6vNsyyu0tQ4FNGi7NrS63avtelyqmXXu0ddFKJoK4XM0ltEso8Jc57KRUTMyNUn3/f7gdk2deQKBgA9VnEXzm8Mnv4QZsQAhyrJJH0mQYgiB0LbBJfaDQ/C/JKvtQOyGuCVopyeZrIpcqRBmVbt765+plivP0a8tkFoV/BYpcd1pNzZp6TDGTz2gHzjm5s8P2cV0NgNbidM1lCDyTRhpDh976fLl7lIr+cEvl0ZSLtfZ2gH8nH5yasUXAoGBAJEIVcswjRFRFEAnugqhlJCuLtgDlezsGuUeK6dAFIVovaiaQGtVw5XyrhKykKsPZVVmzsmU4nPzUaTh5Yv7v92OMWKF3IOnmJhp3Ipem0EnTXnLsVoTy4IfgL2hBd9zlnHsLvTj0cd717WJ9AmEQIdIT1jzWZFVy5j1Pa2ANXWxAoGALoTb3JlHQIhfm0s/WyWlzyyuWm4UFeEzBjo5qBzL49ZOQhAL7y8pFTN1UntXMVq6enS4N0ljaSlUvyRLkgKC1JsQKetS9cOcb2LsAlMklBhMvZUBYSw+cHZcdpHqMZEfT754hXz5Zqa+PXeFC22Af4yG8nlrMKDObwA8M6GayfM=")
                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzwMD/cH8Z8E7GMmoICC5kxkbpQiwwxDTJARdNBO4c+8EV/giVjbGs7VItxE/ryHuZil+9xqlPo/QIIXTBYgKlXJo3QO6+u41G/eh6XqthmgjP7nnm8f3jpieII6HV8E1l/BmUJWFdxqlpTUcAIrgu/RMiar2KjXCoVv0af9QBbacbV1U+1BaC1N8CIeKNhiQAIGeGb72i36vOWSK/4TdfdNGhsDf2E7TKKz0CUx/tkERNsdKxWf2gc6i5MXI1pvt6H2oDzg9i8eVgrFtnvzsic2qg1hJOL0OB6s3akFTHgs50lReltuXDT92PHTlqJK3S5qO2ZAqzbHFFdfTlzZ0BwIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
//                .gatewayUrl("https://open-sea.alipay.com")
                .build();

        String agentToken = "zI7ZHWL4PURWs61dpIyckR1i1dZYZdZtKGgmqCemR00000A1";

        CancelPaymentDTO cancelPayment = new CancelPaymentDTO();
//        inquiryPayment.setPaymentId("202007311140108001001887H0221425716"); // refucnd
        cancelPayment.setPaymentId("202008101140108001001885B0221657289"); // pay ok
        ConsumerPresenter.cancel(agentToken, cancelPayment, config, new AMSObjectCallback<CancelPayment>() {
            @Override
            public void onSuccess(CancelPayment payment) {
                System.out.println();
                System.out.println("============ Response body ============ ");
                System.out.println(JSON.toJSONString(payment, SerializerFeature.PrettyFormat));
            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });


        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //通过了 Acceptance Test 的Test Case 3
    @org.junit.jupiter.api.Test
    void AcceptanceTestForCase3() {
        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y08882Y3EDZ03753")
                .privateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBJO3Tvj7VpW+4vMiaGJet6xUZnP40u6U+4q5kC2W3bsy1vQd6odUE0aqKgJ9G7G+qUdVYwYNsA5Srg1a/AE5u3ZAV75avvxpLPr5zg8+rlGBB+9EWAUysMjrkg13odIqn/fVwrXzoA7TKkUT2/4dYxb7YewV6GSC5Z100Q7DFrVE0AFJmmwDG0i3mmT5zSHSBSLQnCaoZAU8HLEKWVds9Rrb8OE8q297ca25L+po/P5A5k/z2pmXMp4mgslt1rIPvqTbxLMQkMDg4OkOjIjcl8IP2SLK930UM3ML+XrHXs+BdASh81y4hQsJecghQ2QQVHobzHYTQstGe9cNXup2rAgMBAAECggEALv/smfIT1hcmSLpT21j14rK6dB7hmPltkyxWQUywkQh+8elAjo3VA7L2eP+EeABWR0sO9QVAOhA3jA/5gvuuj8SMyp7uXTmUbSwBjfK7onsWaQ0nM/X3HHgPXNi+VXoMeEUBjSdmXN30bwBdc/LdJB6bbRegj6YTdcY7cmjsLZR3SE8P5Llnr3K8tnV/v/SrUe19KTCyW7fdVNqeUmiuGrupaE0/rkumY+XvYIYwMQ4HYoX+clKibUzTHrKgkpOMh1c9+QI5Yf9dypkQ5Y6BFecpHhrnDQLzM/QmdznKOCvRiCl9RgTIl72G45GEv5KW6xh2kZZPUDAo5TRqIpW4UQKBgQDSSgJwzqYZyxxvsyLBh2jxeTNofi8UgiC08bxhIWWeWb+P00NSosZQgGDdK/yS1v1ulchHp6wyofrTRTxsmFJC3tnQaCMUQL5Aqa5mSZpTNcYYOB+aXSUxOk6B0vWYJ2Puj1lxTcFazJkj1JF+z4RtkH8qPGEsceCZxb59Za7fQwKBgQCdN3CFfk/EGgFpOS5xnUTEUNtmL114mCF5My33MQHgvStstlF5us5lzaDaBUgifBoR65uy0KKAMIhHbEhQSeWq+6sC4ncx2wsv6vNsyyu0tQ4FNGi7NrS63avtelyqmXXu0ddFKJoK4XM0ltEso8Jc57KRUTMyNUn3/f7gdk2deQKBgA9VnEXzm8Mnv4QZsQAhyrJJH0mQYgiB0LbBJfaDQ/C/JKvtQOyGuCVopyeZrIpcqRBmVbt765+plivP0a8tkFoV/BYpcd1pNzZp6TDGTz2gHzjm5s8P2cV0NgNbidM1lCDyTRhpDh976fLl7lIr+cEvl0ZSLtfZ2gH8nH5yasUXAoGBAJEIVcswjRFRFEAnugqhlJCuLtgDlezsGuUeK6dAFIVovaiaQGtVw5XyrhKykKsPZVVmzsmU4nPzUaTh5Yv7v92OMWKF3IOnmJhp3Ipem0EnTXnLsVoTy4IfgL2hBd9zlnHsLvTj0cd717WJ9AmEQIdIT1jzWZFVy5j1Pa2ANXWxAoGALoTb3JlHQIhfm0s/WyWlzyyuWm4UFeEzBjo5qBzL49ZOQhAL7y8pFTN1UntXMVq6enS4N0ljaSlUvyRLkgKC1JsQKetS9cOcb2LsAlMklBhMvZUBYSw+cHZcdpHqMZEfT754hXz5Zqa+PXeFC22Af4yG8nlrMKDObwA8M6GayfM=")
                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzwMD/cH8Z8E7GMmoICC5kxkbpQiwwxDTJARdNBO4c+8EV/giVjbGs7VItxE/ryHuZil+9xqlPo/QIIXTBYgKlXJo3QO6+u41G/eh6XqthmgjP7nnm8f3jpieII6HV8E1l/BmUJWFdxqlpTUcAIrgu/RMiar2KjXCoVv0af9QBbacbV1U+1BaC1N8CIeKNhiQAIGeGb72i36vOWSK/4TdfdNGhsDf2E7TKKz0CUx/tkERNsdKxWf2gc6i5MXI1pvt6H2oDzg9i8eVgrFtnvzsic2qg1hJOL0OB6s3akFTHgs50lReltuXDT92PHTlqJK3S5qO2ZAqzbHFFdfTlzZ0BwIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
                .build();

        String agentToken = "zI7ZHWL4PURWs61dpIyckR1i1dZYZdZtKGgmqCemR00000A1";

        long amountInCents = 20000L;
        Currency currency = Currency.getInstance("USD"); //CNY	China Yuan Renminbi
        Amount amount = new Amount(currency, amountInCents);

        System.out.println("Please input a payment code of buyer：");
        Scanner sc = new Scanner(System.in);
        String buyerCode = sc.nextLine();

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


                int cnt = 10;
                while (cnt > 0) {

                    InquiryPaymentDTO inquiryPayment = new InquiryPaymentDTO();
                    inquiryPayment.setPaymentRequestId(paymentRequestId);
                    ConsumerPresenter.inquiry(agentToken, inquiryPayment, config, new AMSObjectCallback<InquiryPayment>() {
                        @Override
                        public void onSuccess(InquiryPayment inquiryPayment) {
                            System.out.println();
                            System.out.println("============ Response body ============ ");
                            System.out.println(JSON.toJSONString(inquiryPayment, SerializerFeature.PrettyFormat));
                        }

                        @Override
                        public void onHeader(HashMap<String, String> headerMap) {
                            System.out.println();
                            System.out.println("============ Response header ============ ");
                            headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
                        }

                        @Override
                        public void onFailure(int code, Exception e) {
                            System.out.println();
                            System.out.println("============ Response onFaileure ============ ");
                            System.out.println("code:" + code + "  exception:" + e.getMessage());

                        }
                    });

                    cnt--;


                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });


        try {
            Thread.sleep(300 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //通过了 Acceptance Test 的Test Case 4
    @org.junit.jupiter.api.Test
    void AcceptanceTestForCase4() {

        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y08882Y3EDZ03753")
                .privateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBJO3Tvj7VpW+4vMiaGJet6xUZnP40u6U+4q5kC2W3bsy1vQd6odUE0aqKgJ9G7G+qUdVYwYNsA5Srg1a/AE5u3ZAV75avvxpLPr5zg8+rlGBB+9EWAUysMjrkg13odIqn/fVwrXzoA7TKkUT2/4dYxb7YewV6GSC5Z100Q7DFrVE0AFJmmwDG0i3mmT5zSHSBSLQnCaoZAU8HLEKWVds9Rrb8OE8q297ca25L+po/P5A5k/z2pmXMp4mgslt1rIPvqTbxLMQkMDg4OkOjIjcl8IP2SLK930UM3ML+XrHXs+BdASh81y4hQsJecghQ2QQVHobzHYTQstGe9cNXup2rAgMBAAECggEALv/smfIT1hcmSLpT21j14rK6dB7hmPltkyxWQUywkQh+8elAjo3VA7L2eP+EeABWR0sO9QVAOhA3jA/5gvuuj8SMyp7uXTmUbSwBjfK7onsWaQ0nM/X3HHgPXNi+VXoMeEUBjSdmXN30bwBdc/LdJB6bbRegj6YTdcY7cmjsLZR3SE8P5Llnr3K8tnV/v/SrUe19KTCyW7fdVNqeUmiuGrupaE0/rkumY+XvYIYwMQ4HYoX+clKibUzTHrKgkpOMh1c9+QI5Yf9dypkQ5Y6BFecpHhrnDQLzM/QmdznKOCvRiCl9RgTIl72G45GEv5KW6xh2kZZPUDAo5TRqIpW4UQKBgQDSSgJwzqYZyxxvsyLBh2jxeTNofi8UgiC08bxhIWWeWb+P00NSosZQgGDdK/yS1v1ulchHp6wyofrTRTxsmFJC3tnQaCMUQL5Aqa5mSZpTNcYYOB+aXSUxOk6B0vWYJ2Puj1lxTcFazJkj1JF+z4RtkH8qPGEsceCZxb59Za7fQwKBgQCdN3CFfk/EGgFpOS5xnUTEUNtmL114mCF5My33MQHgvStstlF5us5lzaDaBUgifBoR65uy0KKAMIhHbEhQSeWq+6sC4ncx2wsv6vNsyyu0tQ4FNGi7NrS63avtelyqmXXu0ddFKJoK4XM0ltEso8Jc57KRUTMyNUn3/f7gdk2deQKBgA9VnEXzm8Mnv4QZsQAhyrJJH0mQYgiB0LbBJfaDQ/C/JKvtQOyGuCVopyeZrIpcqRBmVbt765+plivP0a8tkFoV/BYpcd1pNzZp6TDGTz2gHzjm5s8P2cV0NgNbidM1lCDyTRhpDh976fLl7lIr+cEvl0ZSLtfZ2gH8nH5yasUXAoGBAJEIVcswjRFRFEAnugqhlJCuLtgDlezsGuUeK6dAFIVovaiaQGtVw5XyrhKykKsPZVVmzsmU4nPzUaTh5Yv7v92OMWKF3IOnmJhp3Ipem0EnTXnLsVoTy4IfgL2hBd9zlnHsLvTj0cd717WJ9AmEQIdIT1jzWZFVy5j1Pa2ANXWxAoGALoTb3JlHQIhfm0s/WyWlzyyuWm4UFeEzBjo5qBzL49ZOQhAL7y8pFTN1UntXMVq6enS4N0ljaSlUvyRLkgKC1JsQKetS9cOcb2LsAlMklBhMvZUBYSw+cHZcdpHqMZEfT754hXz5Zqa+PXeFC22Af4yG8nlrMKDObwA8M6GayfM=")
                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzwMD/cH8Z8E7GMmoICC5kxkbpQiwwxDTJARdNBO4c+8EV/giVjbGs7VItxE/ryHuZil+9xqlPo/QIIXTBYgKlXJo3QO6+u41G/eh6XqthmgjP7nnm8f3jpieII6HV8E1l/BmUJWFdxqlpTUcAIrgu/RMiar2KjXCoVv0af9QBbacbV1U+1BaC1N8CIeKNhiQAIGeGb72i36vOWSK/4TdfdNGhsDf2E7TKKz0CUx/tkERNsdKxWf2gc6i5MXI1pvt6H2oDzg9i8eVgrFtnvzsic2qg1hJOL0OB6s3akFTHgs50lReltuXDT92PHTlqJK3S5qO2ZAqzbHFFdfTlzZ0BwIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
                .build();

        String agentToken = "zI7ZHWL4PURWs61dpIyckR1i1dZYZdZtKGgmqCemR00000A1";

        long amountInCents = 20000L;
        Currency currency = Currency.getInstance("USD"); //CNY	China Yuan Renminbi
        Amount amount = new Amount(currency, amountInCents);

        System.out.println("Please input a payment code of buyer：");
        Scanner sc = new Scanner(System.in);
        String buyerCode = sc.nextLine();

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

                //暂停程序，上存图片
//                System.out.println("Please do step 2 upload image. Press Enter to continue.");
//                Scanner scannerAfterPay = new Scanner(System.in);

                System.out.println("Please do step 2 upload image. ");
                int sec = 20;
                while (sec > 0) {
                    try {
                        Thread.sleep(1 * 1000);
                        sec--;
                        System.out.println(" remaining " + sec + " seconds");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                Instant start = Instant.now();
                Instant end = Instant.now();
                long timeElapsed = 0;
                //发起query 10次
                int cnt = 10;
                while (cnt > 0 && timeElapsed <= 50 * 1000) {

                    InquiryPaymentDTO inquiryPayment = new InquiryPaymentDTO();
                    inquiryPayment.setPaymentRequestId(paymentRequestId);
                    ConsumerPresenter.inquiry(agentToken, inquiryPayment, config, new AMSObjectCallback<InquiryPayment>() {
                        @Override
                        public void onSuccess(InquiryPayment inquiryPayment) {
                            System.out.println();
                            System.out.println("============ Response body ============ ");
                            System.out.println(JSON.toJSONString(inquiryPayment, SerializerFeature.PrettyFormat));
                        }

                        @Override
                        public void onHeader(HashMap<String, String> headerMap) {
                            System.out.println();
                            System.out.println("============ Response header ============ ");
                            headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
                        }

                        @Override
                        public void onFailure(int code, Exception e) {
                            System.out.println();
                            System.out.println("============ Response onFaileure ============ ");
                            System.out.println("code:" + code + "  exception:" + e.getMessage());

                        }
                    });

                    cnt--;

                    try {
                        Thread.sleep(4 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    end = Instant.now();
                    timeElapsed = Duration.between(start, end).toMillis();

                    System.out.println("timeElapsed: " + timeElapsed + " Minute: " + timeElapsed / 60 / 1000);
                }


                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //发起3次cancel
                for (int i = 0; i < 5; i++) {
                    CancelPaymentDTO cancelPayment = new CancelPaymentDTO();
                    cancelPayment.setPaymentRequestId(paymentRequestId);
                    ConsumerPresenter.cancel(agentToken, cancelPayment, config, new AMSObjectCallback<CancelPayment>() {
                        @Override
                        public void onSuccess(CancelPayment payment) {
                            System.out.println();
                            System.out.println("============ Response body ============ ");
                            System.out.println(JSON.toJSONString(payment, SerializerFeature.PrettyFormat));
                        }

                        @Override
                        public void onHeader(HashMap<String, String> headerMap) {
                            System.out.println();
                            System.out.println("============ Response header ============ ");
                            headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
                        }

                        @Override
                        public void onFailure(int code, Exception e) {
                            System.out.println();
                            System.out.println("============ Response onFaileure ============ ");
                            System.out.println("code:" + code + "  exception:" + e.getMessage());

                        }
                    });

                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });

        System.out.println("send cancel -> ");
        Scanner scCancel = new Scanner(System.in);
        String bbbb = scCancel.nextLine();




//        while (true) {
//            try {
//                Thread.sleep(10 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    //通过了 Acceptance Test 的Test Case 7
    @org.junit.jupiter.api.Test
    void AcceptanceTestForCase7() {
        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y08882Y3EDZ03753")
                .privateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBJO3Tvj7VpW+4vMiaGJet6xUZnP40u6U+4q5kC2W3bsy1vQd6odUE0aqKgJ9G7G+qUdVYwYNsA5Srg1a/AE5u3ZAV75avvxpLPr5zg8+rlGBB+9EWAUysMjrkg13odIqn/fVwrXzoA7TKkUT2/4dYxb7YewV6GSC5Z100Q7DFrVE0AFJmmwDG0i3mmT5zSHSBSLQnCaoZAU8HLEKWVds9Rrb8OE8q297ca25L+po/P5A5k/z2pmXMp4mgslt1rIPvqTbxLMQkMDg4OkOjIjcl8IP2SLK930UM3ML+XrHXs+BdASh81y4hQsJecghQ2QQVHobzHYTQstGe9cNXup2rAgMBAAECggEALv/smfIT1hcmSLpT21j14rK6dB7hmPltkyxWQUywkQh+8elAjo3VA7L2eP+EeABWR0sO9QVAOhA3jA/5gvuuj8SMyp7uXTmUbSwBjfK7onsWaQ0nM/X3HHgPXNi+VXoMeEUBjSdmXN30bwBdc/LdJB6bbRegj6YTdcY7cmjsLZR3SE8P5Llnr3K8tnV/v/SrUe19KTCyW7fdVNqeUmiuGrupaE0/rkumY+XvYIYwMQ4HYoX+clKibUzTHrKgkpOMh1c9+QI5Yf9dypkQ5Y6BFecpHhrnDQLzM/QmdznKOCvRiCl9RgTIl72G45GEv5KW6xh2kZZPUDAo5TRqIpW4UQKBgQDSSgJwzqYZyxxvsyLBh2jxeTNofi8UgiC08bxhIWWeWb+P00NSosZQgGDdK/yS1v1ulchHp6wyofrTRTxsmFJC3tnQaCMUQL5Aqa5mSZpTNcYYOB+aXSUxOk6B0vWYJ2Puj1lxTcFazJkj1JF+z4RtkH8qPGEsceCZxb59Za7fQwKBgQCdN3CFfk/EGgFpOS5xnUTEUNtmL114mCF5My33MQHgvStstlF5us5lzaDaBUgifBoR65uy0KKAMIhHbEhQSeWq+6sC4ncx2wsv6vNsyyu0tQ4FNGi7NrS63avtelyqmXXu0ddFKJoK4XM0ltEso8Jc57KRUTMyNUn3/f7gdk2deQKBgA9VnEXzm8Mnv4QZsQAhyrJJH0mQYgiB0LbBJfaDQ/C/JKvtQOyGuCVopyeZrIpcqRBmVbt765+plivP0a8tkFoV/BYpcd1pNzZp6TDGTz2gHzjm5s8P2cV0NgNbidM1lCDyTRhpDh976fLl7lIr+cEvl0ZSLtfZ2gH8nH5yasUXAoGBAJEIVcswjRFRFEAnugqhlJCuLtgDlezsGuUeK6dAFIVovaiaQGtVw5XyrhKykKsPZVVmzsmU4nPzUaTh5Yv7v92OMWKF3IOnmJhp3Ipem0EnTXnLsVoTy4IfgL2hBd9zlnHsLvTj0cd717WJ9AmEQIdIT1jzWZFVy5j1Pa2ANXWxAoGALoTb3JlHQIhfm0s/WyWlzyyuWm4UFeEzBjo5qBzL49ZOQhAL7y8pFTN1UntXMVq6enS4N0ljaSlUvyRLkgKC1JsQKetS9cOcb2LsAlMklBhMvZUBYSw+cHZcdpHqMZEfT754hXz5Zqa+PXeFC22Af4yG8nlrMKDObwA8M6GayfM=")
                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzwMD/cH8Z8E7GMmoICC5kxkbpQiwwxDTJARdNBO4c+8EV/giVjbGs7VItxE/ryHuZil+9xqlPo/QIIXTBYgKlXJo3QO6+u41G/eh6XqthmgjP7nnm8f3jpieII6HV8E1l/BmUJWFdxqlpTUcAIrgu/RMiar2KjXCoVv0af9QBbacbV1U+1BaC1N8CIeKNhiQAIGeGb72i36vOWSK/4TdfdNGhsDf2E7TKKz0CUx/tkERNsdKxWf2gc6i5MXI1pvt6H2oDzg9i8eVgrFtnvzsic2qg1hJOL0OB6s3akFTHgs50lReltuXDT92PHTlqJK3S5qO2ZAqzbHFFdfTlzZ0BwIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
                .build();

        String agentToken = "zI7ZHWL4PURWs61dpIyckR1i1dZYZdZtKGgmqCemR00000A1";

        long amountInCents = 20000L;
        Currency currency = Currency.getInstance("USD"); //CNY	China Yuan Renminbi
        Amount amount = new Amount(currency, amountInCents);

        System.out.println("Please input a payment code of buyer：");
        Scanner sc = new Scanner(System.in);
        String buyerCode = sc.nextLine();

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


                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        timer.cancel();

                        Instant start = Instant.now();
                        Instant end = Instant.now();
                        long timeElapsed = 0;
                        while (timeElapsed / 1000 < 2 * 60) {
                            CancelPaymentDTO cancelPayment = new CancelPaymentDTO();
                            cancelPayment.setPaymentRequestId(paymentRequestId);
                            ConsumerPresenter.cancel(agentToken, cancelPayment, config, new AMSObjectCallback<CancelPayment>() {
                                @Override
                                public void onSuccess(CancelPayment payment) {
                                    System.out.println();
                                    System.out.println("============ Response body ============ ");
                                    System.out.println(JSON.toJSONString(payment, SerializerFeature.PrettyFormat));
                                }

                                @Override
                                public void onHeader(HashMap<String, String> headerMap) {
                                    System.out.println();
                                    System.out.println("============ Response header ============ ");
                                    headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
                                }

                                @Override
                                public void onFailure(int code, Exception e) {
                                    System.out.println();
                                    System.out.println("============ Response onFaileure ============ ");
                                    System.out.println("code:" + code + "  exception:" + e.getMessage());

                                }
                            });


                            try {
                                Thread.sleep(3 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            end = Instant.now();

                            timeElapsed = Duration.between(start, end).toMillis();

                            System.out.println("timeElapsed: " + timeElapsed);
                        }
                    }
                }, 50 * 1000, 60 * 1000);


            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });


        try {
            Thread.sleep(300 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //通过了 Acceptance Test 的Test Case 9
    @org.junit.jupiter.api.Test
    void AcceptanceTestForCase9() {
        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y08882Y3EDZ03753")
                .privateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBJO3Tvj7VpW+4vMiaGJet6xUZnP40u6U+4q5kC2W3bsy1vQd6odUE0aqKgJ9G7G+qUdVYwYNsA5Srg1a/AE5u3ZAV75avvxpLPr5zg8+rlGBB+9EWAUysMjrkg13odIqn/fVwrXzoA7TKkUT2/4dYxb7YewV6GSC5Z100Q7DFrVE0AFJmmwDG0i3mmT5zSHSBSLQnCaoZAU8HLEKWVds9Rrb8OE8q297ca25L+po/P5A5k/z2pmXMp4mgslt1rIPvqTbxLMQkMDg4OkOjIjcl8IP2SLK930UM3ML+XrHXs+BdASh81y4hQsJecghQ2QQVHobzHYTQstGe9cNXup2rAgMBAAECggEALv/smfIT1hcmSLpT21j14rK6dB7hmPltkyxWQUywkQh+8elAjo3VA7L2eP+EeABWR0sO9QVAOhA3jA/5gvuuj8SMyp7uXTmUbSwBjfK7onsWaQ0nM/X3HHgPXNi+VXoMeEUBjSdmXN30bwBdc/LdJB6bbRegj6YTdcY7cmjsLZR3SE8P5Llnr3K8tnV/v/SrUe19KTCyW7fdVNqeUmiuGrupaE0/rkumY+XvYIYwMQ4HYoX+clKibUzTHrKgkpOMh1c9+QI5Yf9dypkQ5Y6BFecpHhrnDQLzM/QmdznKOCvRiCl9RgTIl72G45GEv5KW6xh2kZZPUDAo5TRqIpW4UQKBgQDSSgJwzqYZyxxvsyLBh2jxeTNofi8UgiC08bxhIWWeWb+P00NSosZQgGDdK/yS1v1ulchHp6wyofrTRTxsmFJC3tnQaCMUQL5Aqa5mSZpTNcYYOB+aXSUxOk6B0vWYJ2Puj1lxTcFazJkj1JF+z4RtkH8qPGEsceCZxb59Za7fQwKBgQCdN3CFfk/EGgFpOS5xnUTEUNtmL114mCF5My33MQHgvStstlF5us5lzaDaBUgifBoR65uy0KKAMIhHbEhQSeWq+6sC4ncx2wsv6vNsyyu0tQ4FNGi7NrS63avtelyqmXXu0ddFKJoK4XM0ltEso8Jc57KRUTMyNUn3/f7gdk2deQKBgA9VnEXzm8Mnv4QZsQAhyrJJH0mQYgiB0LbBJfaDQ/C/JKvtQOyGuCVopyeZrIpcqRBmVbt765+plivP0a8tkFoV/BYpcd1pNzZp6TDGTz2gHzjm5s8P2cV0NgNbidM1lCDyTRhpDh976fLl7lIr+cEvl0ZSLtfZ2gH8nH5yasUXAoGBAJEIVcswjRFRFEAnugqhlJCuLtgDlezsGuUeK6dAFIVovaiaQGtVw5XyrhKykKsPZVVmzsmU4nPzUaTh5Yv7v92OMWKF3IOnmJhp3Ipem0EnTXnLsVoTy4IfgL2hBd9zlnHsLvTj0cd717WJ9AmEQIdIT1jzWZFVy5j1Pa2ANXWxAoGALoTb3JlHQIhfm0s/WyWlzyyuWm4UFeEzBjo5qBzL49ZOQhAL7y8pFTN1UntXMVq6enS4N0ljaSlUvyRLkgKC1JsQKetS9cOcb2LsAlMklBhMvZUBYSw+cHZcdpHqMZEfT754hXz5Zqa+PXeFC22Af4yG8nlrMKDObwA8M6GayfM=")
                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzwMD/cH8Z8E7GMmoICC5kxkbpQiwwxDTJARdNBO4c+8EV/giVjbGs7VItxE/ryHuZil+9xqlPo/QIIXTBYgKlXJo3QO6+u41G/eh6XqthmgjP7nnm8f3jpieII6HV8E1l/BmUJWFdxqlpTUcAIrgu/RMiar2KjXCoVv0af9QBbacbV1U+1BaC1N8CIeKNhiQAIGeGb72i36vOWSK/4TdfdNGhsDf2E7TKKz0CUx/tkERNsdKxWf2gc6i5MXI1pvt6H2oDzg9i8eVgrFtnvzsic2qg1hJOL0OB6s3akFTHgs50lReltuXDT92PHTlqJK3S5qO2ZAqzbHFFdfTlzZ0BwIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
                .build();

        String agentToken = "zI7ZHWL4PURWs61dpIyckR1i1dZYZdZtKGgmqCemR00000A1";

        long amountInCents = 20000L;
        Currency currency = Currency.getInstance("USD"); //CNY	China Yuan Renminbi
        Amount amount = new Amount(currency, amountInCents);

        System.out.println("Please input a payment code of buyer：");
        Scanner sc = new Scanner(System.in);
        String buyerCode = sc.nextLine();

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


                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        timer.cancel();

                        Instant start = Instant.now();
                        Instant end = Instant.now();
                        long timeElapsed = 0;
                        while (timeElapsed / 1000 < 2 * 60) {
                            CancelPaymentDTO cancelPayment = new CancelPaymentDTO();
                            cancelPayment.setPaymentRequestId(paymentRequestId);
                            ConsumerPresenter.cancel(agentToken, cancelPayment, config, new AMSObjectCallback<CancelPayment>() {
                                @Override
                                public void onSuccess(CancelPayment payment) {
                                    System.out.println();
                                    System.out.println("============ Response body ============ ");
                                    System.out.println(JSON.toJSONString(payment, SerializerFeature.PrettyFormat));
                                }

                                @Override
                                public void onHeader(HashMap<String, String> headerMap) {
                                    System.out.println();
                                    System.out.println("============ Response header ============ ");
                                    headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
                                }

                                @Override
                                public void onFailure(int code, Exception e) {
                                    System.out.println();
                                    System.out.println("============ Response onFaileure ============ ");
                                    System.out.println("code:" + code + "  exception:" + e.getMessage());

                                }
                            });


                            try {
                                Thread.sleep(3 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            end = Instant.now();

                            timeElapsed = Duration.between(start, end).toMillis();

                            System.out.println("timeElapsed: " + timeElapsed);
                        }
                    }
                }, 10 * 1000, 60 * 1000);


            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });


        try {
            Thread.sleep(300 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}