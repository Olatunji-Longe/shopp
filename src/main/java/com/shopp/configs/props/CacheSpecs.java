package com.shopp.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "caching.specs")
public class CacheSpecs {

    private Long timeoutSecs = 60L;
    private Map<String, CacheSetting> settings = new HashMap<>();

    public Long getTimeoutSecs() {
        return timeoutSecs;
    }

    public void setTimeoutSecs(Long timeoutSecs) {
        this.timeoutSecs = timeoutSecs;
    }

    public Map<String, CacheSetting> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, CacheSetting> settings) {
        this.settings = settings;
    }

    public static class CacheSetting {
        private Long minCapacity = 100L;
        private Long maxCapacity = 500L;
        private Long ttlSecs = 120L;

        public Long getMinCapacity() {
            return minCapacity;
        }

        public void setMinCapacity(Long minCapacity) {
            this.minCapacity = minCapacity;
        }

        public Long getMaxCapacity() {
            return maxCapacity;
        }

        public void setMaxCapacity(Long maxCapacity) {
            this.maxCapacity = maxCapacity;
        }

        public Long getTtlSecs() {
            return ttlSecs;
        }

        public void setTtlSecs(Long ttlSecs) {
            this.ttlSecs = ttlSecs;
        }

        @Override
        public String toString() {
            return "CacheSetting{" +
                    " minCapacity=" + minCapacity +
                    ", maxCapacity=" + maxCapacity +
                    ", ttlSecs=" + ttlSecs +
                    '}';
        }
    }

}
