package com.ming.alipay.util;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/8/2020 12:04 AM
 */
class SignatureUtilTest {

    @Test
    void sign() throws UnsupportedEncodingException {
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCjQIa+O6xT3Ak+dZSH6Gg/O9poiykd1pXmYD9LjS4I2dRr5KEP8br0fiF7hr++1TW4+ahd/peiWQuBehuRIGbODP3n+awdaoA/m2zpU2m5YlBuPMG6TAp0b03qj12SPX3XnxLllpAnOi/ATEo9x9wD66COXnToMJHbiGbGK9dt9ZWUta4Vp79qa42T0301J37px8/jrR4nznplrRyW6Tu3Q6LC86cCP4QkoHGP/kr3LMBU1iBfvZelM27DcAd2ENG8vWqyfyEWgiecP3m4Xfm4C/9WY6QDBLxttk+iBYYDzj4v8Qf/foCOEh8ViSeoQmT3Yu1dPPCbuqOWF7kEMJszAgMBAAECggEALzllQPZmHUQTcHv24XG7Zj9cKM3IsRb3+dJxvNs0K99HcYaLiN82Y69w5BjQstVeWwntSHNzTcVQbL3z54Fl+8kKUeaJgWDjhILjeINmm/fyqFDvAYMpqxDfizC5sqoTEyKzBrMNNCvC0G/BMin0XTX2FfkA/IHAT68fe8gG44mmJDC4mLUcqnZ8lZxEWqCiwAcZlcez7toIIDFA/E7pvE/dBF06S65ftZTDOfL+cR17qOSkH3HNvbdQpJvBq0ea2vR6NXEhrqmlesP8tntuUidfxpXpAwVaMCaAGj/gvHvoRyCeFAeuWGiPenMJ3ABSGZcBumtMdQK3fRx/qkZKIQKBgQDkLZS6M/O+EbcBYvfWIPCRFQA0YUwPNCrpZMEZmUIsHLMTz+33L413Al8mJQFhP4JKZR+mz/knBd+z4zpYj3vV2Tdv9qVpG09OrwiJ4O2rxgNQPi2BeURQ33Q4p5njanJyX7vYKcTxHBFSoIdfzQh3PPn8zyLHrWB/LFvQWIbnawKBgQC3KFNdPnMI4yDANIT8yY2892ehKzyoINlnwsKRIous2XFLE9Ueg0I5J+uQMgADdCYyVSW0B95nm8ZewXhjDQXYO1v+pqG+rojc6wjDB85wtYrOEn1+NuqCAVpdnlqGv/hRno6CjHwkJSD3MhOS/U6InWqYp6MS6oc8v6uxs2M1WQKBgQCjdROqIDVn34Dd+WvZdVdgV08CNwEpkxF6iLbcA7Inr5eJhdbO0L3il0xaIwBODhTj/nUiJrIxhj+uugS+FGt90lGoX2Q3W10A3NqdCwi3mO4euawXqCuMU8cKHuZax5Kc5H/IZoOyxYyTSHQx6Ms7v+7FcA1WRZlvAPP02LYraQKBgFWB2gl6EjJf9vZEjqGFZ0qFJjgz+0lyrIyofDVNr2+uxBmM1L4+ATi1zl+vOBpkq0BsSAHYephcPSwXnqB/f+8HJ1ena3Od//9DKwACMPqlhnvWXggCk1DGWO3D8/dcwA06vMVt2Lb3LoC5PDIvV+6nrxA9kwBNxX0y7nEzkmlhAoGBAJ6ceKPNCfrwc+IGrmUf0Ny5ly2XP+OEE8PL4nPCFOqG+YA4vlmngsQ7KhbmOXCg8lnQtZj70sbj48kqLLxEuVwqpdI/5ViqXdHaMMWk1Rr9BYMTL4nrS9LnvU2tzYOx7faJRhtkIv57SLI709auIxH1dJMglo9rJXpFIc84r8jA";
        String content =  "MING";

        String signed = SignatureUtil.sign(privateKey,content);

        System.out.println(signed);
        assertEquals("KEfWzU/MM60n6ZLjKbsJe86QqaqYIM4jNkNW4J1Y4Cg7UA4Bwqd/sMDuhQWtTe7ams3jDOm5VR6ZVcF7OkuQfXfl1hJSTNVN05idnOlEzvztMfQBYtC6xj/BW+KKggjsLZtEnly0M9RsPWMhuzPtyWb3BywfcwLDPQlBnExC6ZtZBZcJu0zorUloiUxHh5cs7DmbQHRhh5ExeeQdyo4DwbboAe/2vAxZP057nF2k1TyF2DMxl2lO52AxZLsmJSLZC3rT6JA0im6Bfh4JXdmSbAIZ8DYJV61ELpfS3zrF5gIXebQyx1APz6V3M+WqFdBX+c4KrTzUDP2OtQ/2VHNeiw==",signed);


        String urlencoded =  URLEncoder.encode(signed, "UTF-8");
        assertEquals("KEfWzU%2FMM60n6ZLjKbsJe86QqaqYIM4jNkNW4J1Y4Cg7UA4Bwqd%2FsMDuhQWtTe7ams3jDOm5VR6ZVcF7OkuQfXfl1hJSTNVN05idnOlEzvztMfQBYtC6xj%2FBW%2BKKggjsLZtEnly0M9RsPWMhuzPtyWb3BywfcwLDPQlBnExC6ZtZBZcJu0zorUloiUxHh5cs7DmbQHRhh5ExeeQdyo4DwbboAe%2F2vAxZP057nF2k1TyF2DMxl2lO52AxZLsmJSLZC3rT6JA0im6Bfh4JXdmSbAIZ8DYJV61ELpfS3zrF5gIXebQyx1APz6V3M%2BWqFdBX%2Bc4KrTzUDP2OtQ%2F2VHNeiw%3D%3D",urlencoded);

    }
}