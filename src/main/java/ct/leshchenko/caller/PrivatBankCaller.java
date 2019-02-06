package ct.leshchenko.caller;

import ct.leshchenko.config.ConfigManager;

/**
 * Created by Vitalii Leshchenko on 10.01.2018.
 */
public class PrivatBankCaller {

    private Caller caller;

    public PrivatBankCaller() {

    }

    public PrivatBankCaller(Caller caller) {
        this.caller = caller;
    }

    public String getCurrencies() {
        return caller.getApiCall(ConfigManager.getInstance().properties.getProperty("GET_CURRENCIES_URL"));
    }


}
