package com.first.rest.webservices.exception.constants;

import java.util.stream.Stream;

public enum StatusCode {
    _400("The data given in the payload failed validation."),
    _401("Authentication credentials are required to access this resource."),
    _402("Payment Required"),
    _403("The supplied authentication credentials are not sufficient to access this resource."),
    _404("The resource could not be located based on the information in the request."),
    _405("The resource does not support this HTTP method."),
    _406("Not Acceptable"),
    _407("Proxy Authentication Required"),
    _408("Request Timeout"),
    _409("The resource cannot be created or updated.  Another resource exists or conflicts with the one in the request."),
    _410("Gone"),
    _411("Length Required"),
    _412("Precondition Failed"),
    _413("Request Entity Too Large"),
    _414("Request-URI Too Long"),
    _415("The request must specify the 'Content-Type' header with the value 'application/json'."),
    _416("Requested Range Not Satisfiable"),
    _417("Expectation Failed"),
    _418("I'm a teapot"),
    _420("Enhance Your Calm (Twitter)"),
    _422("Unprocessable Entity"),
    _423("Locked"),
    _424("Failed Dependency"),
    _425("Reserved for WebDAV"),
    _426("Upgrade Required"),
    _428("Precondition Required"),
    _429("The application has sent too many simultaneous requests."),
    _431("Request Header Fields Too Large"),
    _444("No Response"),
    _449("Retry With"),
    _450("Blocked by Windows Parental Controls"),
    _451("Unavailable For Legal Reasons"),
    _499("Client Closed Request"),
    _500("The server has encountered an error.  Please try again or contact support."),
    _501("Not Implemented"),
    _502("Bad Gateway"),
    _503("The request cannot be serviced at this time.  Please wait a few minutes and try again."),
    _504("Gateway Timeout"),
    _505("HTTP Version Not Supported"),
    _506("Variant Also Negotiates"),
    _507("Insufficient Storage"),
    _508("Loop Detected"),
    _509("Bandwidth Limit Exceeded"),
    _510("Not Extended"),
    _511("Network Authentication Required"),
    _598("Network read timeout error"),
    _599("Network connect timeout error");

    private final String description;

    StatusCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String[] names() {
        return Stream.of(com.first.rest.webservices.exception.constants.StatusCode.values())
                .map(com.first.rest.webservices.exception.constants.StatusCode::name)
                .toArray(String[]::new);
    }
}
