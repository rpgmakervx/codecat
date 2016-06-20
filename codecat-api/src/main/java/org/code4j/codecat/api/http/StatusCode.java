package org.code4j.codecat.api.http;

/**
 * Description :
 * Created by YangZH on 16-6-20
 * 上午1:08
 */

public class StatusCode {
    /**
     * 100 Continue
     */
    public static final StatusCode CONTINUE = newStatus(100, "Continue");

    /**
     * 101 Switching Protocols
     */
    public static final StatusCode SWITCHING_PROTOCOLS = newStatus(101, "Switching Protocols");

    /**
     * 102 Processing (WebDAV, RFC2518)
     */
    public static final StatusCode PROCESSING = newStatus(102, "Processing");

    /**
     * 200 OK
     */
    public static final StatusCode OK = newStatus(200, "OK");

    /**
     * 201 Created
     */
    public static final StatusCode CREATED = newStatus(201, "Created");

    /**
     * 202 Accepted
     */
    public static final StatusCode ACCEPTED = newStatus(202, "Accepted");

    /**
     * 203 Non-Authoritative Information (since HTTP/1.1)
     */
    public static final StatusCode NON_AUTHORITATIVE_INFORMATION =
            newStatus(203, "Non-Authoritative Information");

    /**
     * 204 No Content
     */
    public static final StatusCode NO_CONTENT = newStatus(204, "No Content");

    /**
     * 205 Reset Content
     */
    public static final StatusCode RESET_CONTENT = newStatus(205, "Reset Content");

    /**
     * 206 Partial Content
     */
    public static final StatusCode PARTIAL_CONTENT = newStatus(206, "Partial Content");

    /**
     * 207 Multi-Status (WebDAV, RFC2518)
     */
    public static final StatusCode MULTI_STATUS = newStatus(207, "Multi-Status");

    /**
     * 300 Multiple Choices
     */
    public static final StatusCode MULTIPLE_CHOICES = newStatus(300, "Multiple Choices");

    /**
     * 301 Moved Permanently
     */
    public static final StatusCode MOVED_PERMANENTLY = newStatus(301, "Moved Permanently");

    /**
     * 302 Found
     */
    public static final StatusCode FOUND = newStatus(302, "Found");

    /**
     * 303 See Other (since HTTP/1.1)
     */
    public static final StatusCode SEE_OTHER = newStatus(303, "See Other");

    /**
     * 304 Not Modified
     */
    public static final StatusCode NOT_MODIFIED = newStatus(304, "Not Modified");

    /**
     * 305 Use Proxy (since HTTP/1.1)
     */
    public static final StatusCode USE_PROXY = newStatus(305, "Use Proxy");

    /**
     * 307 Temporary Redirect (since HTTP/1.1)
     */
    public static final StatusCode TEMPORARY_REDIRECT = newStatus(307, "Temporary Redirect");

    /**
     * 400 Bad Request
     */
    public static final StatusCode BAD_REQUEST = newStatus(400, "Bad Request");

    /**
     * 401 Unauthorized
     */
    public static final StatusCode UNAUTHORIZED = newStatus(401, "Unauthorized");

    /**
     * 402 Payment Required
     */
    public static final StatusCode PAYMENT_REQUIRED = newStatus(402, "Payment Required");

    /**
     * 403 Forbidden
     */
    public static final StatusCode FORBIDDEN = newStatus(403, "Forbidden");

    /**
     * 404 Not Found
     */
    public static final StatusCode NOT_FOUND = newStatus(404, "Not Found");

    /**
     * 405 Method Not Allowed
     */
    public static final StatusCode METHOD_NOT_ALLOWED = newStatus(405, "Method Not Allowed");

    /**
     * 406 Not Acceptable
     */
    public static final StatusCode NOT_ACCEPTABLE = newStatus(406, "Not Acceptable");

    /**
     * 407 Proxy Authentication Required
     */
    public static final StatusCode PROXY_AUTHENTICATION_REQUIRED =
            newStatus(407, "Proxy Authentication Required");

    /**
     * 408 Request Timeout
     */
    public static final StatusCode REQUEST_TIMEOUT = newStatus(408, "Request Timeout");

    /**
     * 409 Conflict
     */
    public static final StatusCode CONFLICT = newStatus(409, "Conflict");

    /**
     * 410 Gone
     */
    public static final StatusCode GONE = newStatus(410, "Gone");

    /**
     * 411 Length Required
     */
    public static final StatusCode LENGTH_REQUIRED = newStatus(411, "Length Required");

    /**
     * 412 Precondition Failed
     */
    public static final StatusCode PRECONDITION_FAILED = newStatus(412, "Precondition Failed");

    /**
     * 413 Request Entity Too Large
     */
    public static final StatusCode REQUEST_ENTITY_TOO_LARGE =
            newStatus(413, "Request Entity Too Large");

    /**
     * 414 Request-URI Too Long
     */
    public static final StatusCode REQUEST_URI_TOO_LONG = newStatus(414, "Request-URI Too Long");

    /**
     * 415 Unsupported Media Type
     */
    public static final StatusCode UNSUPPORTED_MEDIA_TYPE = newStatus(415, "Unsupported Media Type");

    /**
     * 416 Requested Range Not Satisfiable
     */
    public static final StatusCode REQUESTED_RANGE_NOT_SATISFIABLE =
            newStatus(416, "Requested Range Not Satisfiable");

    /**
     * 417 Expectation Failed
     */
    public static final StatusCode EXPECTATION_FAILED = newStatus(417, "Expectation Failed");

    /**
     * 421 Misdirected Request
     *
     * <a href="https://tools.ietf.org/html/draft-ietf-httpbis-http2-15#section-9.1.2">421 Status Code</a>
     */
    public static final StatusCode MISDIRECTED_REQUEST = newStatus(421, "Misdirected Request");

    /**
     * 422 Unprocessable Entity (WebDAV, RFC4918)
     */
    public static final StatusCode UNPROCESSABLE_ENTITY = newStatus(422, "Unprocessable Entity");

    /**
     * 423 Locked (WebDAV, RFC4918)
     */
    public static final StatusCode LOCKED = newStatus(423, "Locked");

    /**
     * 424 Failed Dependency (WebDAV, RFC4918)
     */
    public static final StatusCode FAILED_DEPENDENCY = newStatus(424, "Failed Dependency");

    /**
     * 425 Unordered Collection (WebDAV, RFC3648)
     */
    public static final StatusCode UNORDERED_COLLECTION = newStatus(425, "Unordered Collection");

    /**
     * 426 Upgrade Required (RFC2817)
     */
    public static final StatusCode UPGRADE_REQUIRED = newStatus(426, "Upgrade Required");

    /**
     * 428 Precondition Required (RFC6585)
     */
    public static final StatusCode PRECONDITION_REQUIRED = newStatus(428, "Precondition Required");

    /**
     * 429 Too Many Requests (RFC6585)
     */
    public static final StatusCode TOO_MANY_REQUESTS = newStatus(429, "Too Many Requests");

    /**
     * 431 Request Header Fields Too Large (RFC6585)
     */
    public static final StatusCode REQUEST_HEADER_FIELDS_TOO_LARGE =
            newStatus(431, "Request Header Fields Too Large");

    /**
     * 500 Internal Server Error
     */
    public static final StatusCode INTERNAL_SERVER_ERROR = newStatus(500, "Internal Server Error");

    /**
     * 501 Not Implemented
     */
    public static final StatusCode NOT_IMPLEMENTED = newStatus(501, "Not Implemented");

    /**
     * 502 Bad Gateway
     */
    public static final StatusCode BAD_GATEWAY = newStatus(502, "Bad Gateway");

    /**
     * 503 Service Unavailable
     */
    public static final StatusCode SERVICE_UNAVAILABLE = newStatus(503, "Service Unavailable");

    /**
     * 504 Gateway Timeout
     */
    public static final StatusCode GATEWAY_TIMEOUT = newStatus(504, "Gateway Timeout");

    /**
     * 505 HTTP Version Not Supported
     */
    public static final StatusCode HTTP_VERSION_NOT_SUPPORTED =
            newStatus(505, "HTTP Version Not Supported");

    /**
     * 506 Variant Also Negotiates (RFC2295)
     */
    public static final StatusCode VARIANT_ALSO_NEGOTIATES = newStatus(506, "Variant Also Negotiates");

    /**
     * 507 Insufficient Storage (WebDAV, RFC4918)
     */
    public static final StatusCode INSUFFICIENT_STORAGE = newStatus(507, "Insufficient Storage");

    /**
     * 510 Not Extended (RFC2774)
     */
    public static final StatusCode NOT_EXTENDED = newStatus(510, "Not Extended");

    /**
     * 511 Network Authentication Required (RFC6585)
     */
    public static final StatusCode NETWORK_AUTHENTICATION_REQUIRED =
            newStatus(511, "Network Authentication Required");

    private static StatusCode newStatus(int code,String reasonPhrase){
        return null;
    }
}
