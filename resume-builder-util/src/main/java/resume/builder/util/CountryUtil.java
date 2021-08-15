package resume.builder.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class CountryUtil {
    private static final List<Locale> countryLocales = Arrays.stream(Locale.getISOCountries())
            .map(isoCountry -> new Locale("", isoCountry))
            .collect(Collectors.toList());

    public static List<String> getCountryISO(){
        return countryLocales.stream()
                .map(Locale::getISO3Country)
                .collect(Collectors.toList());
    }

    public static List<String> getCountryCodes(){
        return countryLocales.stream()
                .map(Locale::getCountry)
                .collect(Collectors.toList());
    }

    public static List<String> getCountryNames(Locale locale){
        return countryLocales.stream()
                .map(cl -> cl.getDisplayCountry(locale))
                .collect(Collectors.toList());
    }

    public static List<String> getCountryNames(){
        return getCountryNames(Locale.ENGLISH);
    }

    private static boolean isIncluded(List<String> strings, String value){
        return strings.stream()
                .anyMatch(v -> v.equalsIgnoreCase(value));
    }

    public static boolean isValidCountryISO(String iso){
        return isIncluded(getCountryISO(), iso);
    }

    public static boolean isValidCountryCode(String code){
        return isIncluded(getCountryCodes(), code);
    }

    public static boolean isValidCountryName(String name){
        return isIncluded(getCountryNames(), name);
    }

    public static boolean isValidCountryName(String name, Locale locale){
        return isIncluded(getCountryNames(locale), name);
    }
}
