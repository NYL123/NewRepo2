package com.hertz.digital.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.hertz.digital.platform.service.api.PreviewToken;

/**
 * The Class PreviewTokenImpl.
 *
 * @author deepak.parma
 */
@Component(immediate = true, metatype = false)
@Service(value = PreviewToken.class)
public class PreviewTokenImpl implements PreviewToken {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreviewTokenImpl.class);
    private  Cache<String, String> cache;

    /**
     * Instantiates a new preview token impl.
     */
    public PreviewTokenImpl() {
        super();
        cache = CacheBuilder.newBuilder().maximumSize(2000).expireAfterAccess(5, TimeUnit.MINUTES)
                .removalListener(new RemovalListener<Object, Object>() {
                    public void onRemoval(RemovalNotification<Object, Object> removalObj) {
                        LOGGER.debug("removing -->" + removalObj.getValue());
                    }
                }).build();
    }

    @Override
    public String generate(String token) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        LOGGER.debug("PreviewTokenImpl.generate() method invoked");
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomFgp = new byte[50];
        secureRandom.nextBytes(randomFgp);
        String userFingerprint = DatatypeConverter.printHexBinary(randomFgp);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes("utf-8"));
        // return
        // cache.get(builder.append(DatatypeConverter.printHexBinary(userFingerprintDigest).toString()).toString());
        String fingerprintDigest = DatatypeConverter.printHexBinary(userFingerprintDigest).toString();
        LOGGER.debug("Auth & generated token added to cache map");
        cache.put(fingerprintDigest, token);
        LOGGER.debug("PreviewTokenImpl.generate() method returning token");
        return fingerprintDigest;
    }

    @Override
    public String validate(String token) {
        LOGGER.debug("PreviewTokenImpl.validate() method invoked");
        String tokenFromCache = cache.getIfPresent(token);
        LOGGER.debug("PreviewTokenImpl.validate() method returning token if valid");
        return StringUtils.isNotBlank(tokenFromCache) ? tokenFromCache : StringUtils.EMPTY;
    }
}
