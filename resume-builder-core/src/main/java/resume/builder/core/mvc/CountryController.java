package resume.builder.core.mvc;

import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import resume.builder.util.CountryUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest/country", method = RequestMethod.POST)
class CountryController {
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @RequestMapping(value ="find-country-all")
    List<String> findCountryAll(@RequestParam(value = "locale", required = false) String locale){
        if(Optional.ofNullable(locale).isPresent()){
            return CountryUtil.getCountryNames(LocaleUtils.toLocale(locale));
        }
        else{
            return CountryUtil.getCountryNames();
        }
    }
}
