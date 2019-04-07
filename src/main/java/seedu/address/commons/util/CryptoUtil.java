package seedu.address.commons.util;

import seedu.address.commons.exceptions.IllegalValueException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class CryptoUtil {

    class CryptoThread extends Thread
    {
        private String cryptoType;

        public CryptoThread(String cryptoType) {
            this.cryptoType = cryptoType;
        }

        public void run()
        {
            try
            {
                if (this.cryptoType.equalsIgnoreCase("BTC")) {
                    BTCPrice = fetchBTC();
                }

                else if (this.cryptoType.equalsIgnoreCase("ETH")) {
                    ETHPrice = fetchETH();
                }

                else if (this.cryptoType.equalsIgnoreCase("LTC")) {
                    LTCPrice = fetchLTC();
                }

                else {
                    throw new IllegalValueException("Unknown Crypto");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private static Double BTCPrice = 0.0;
    private static Double ETHPrice = 0.0;
    private static Double LTCPrice = 0.0;
    private static CryptoUtil cryptoUtilInstance = null;

    private Date lastUpdated;

    private CryptoUtil() {
        updateCryptoPrices();
    }

    public static CryptoUtil getInstance() {
        if (cryptoUtilInstance == null) {
            cryptoUtilInstance = new CryptoUtil();
        }

        return cryptoUtilInstance;
    }

    private void updateCryptoPrices() {
        CryptoThread BTCThread = new CryptoThread("BTC");
        CryptoThread ETHThread = new CryptoThread("ETH");
        CryptoThread LTCThread = new CryptoThread("LTC");
        BTCThread.start();
        ETHThread.start();
        LTCThread.start();
        try {
            BTCThread.join();
            ETHThread.join();
            LTCThread.join();
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }
        lastUpdated = new Date();
    }

    private void checkIntervalAndUpdate() {
        Date currentTime = new Date();
        long diff = currentTime.getTime() - lastUpdated.getTime();
        long diffMinutes = diff / (60 * 1000);

        if (diffMinutes > 15) {
            updateCryptoPrices();
        }

    }

    public double getBTC() {
        checkIntervalAndUpdate();
        return BTCPrice;
    }

    public double getETH() {
        checkIntervalAndUpdate();
        return ETHPrice;
    }

    public double getLTC() {
        checkIntervalAndUpdate();
        return LTCPrice;
    }

    private double fetchBTC() {
        return getCrypto("BTC");
    }

    private double fetchETH() {
        return getCrypto("ETH");
    }

    private double fetchLTC() {
        return getCrypto("LTC");
    }

    private double getCrypto(String cryptoType) {
        double price = 0.0;
        try {
            URL url = new URL("https://min-api.cryptocompare.com/data/pricemulti?fsyms="+cryptoType+"&tsyms=SGD");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = br.readLine();
            price = Float.parseFloat(output.substring(14, 19));
            conn.disconnect();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return price;

    }
}
