package software.amazon.encryption.s3.materials;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

final public class DecryptionMaterials {

    // Identifies what sort of crypto algorithms we want to use
    // In ESDK, this is an enum
    private final int _algorithmSuiteId;

    // Additional information passed into encrypted that is required on decryption as well
    // Should NOT contain sensitive information
    private final Map<String, String> _encryptionContext;

    private final byte[] _plaintextDataKey;

    // Unused in here since signing is not supported
    // private final byte[] _signingKey;


    private DecryptionMaterials(Builder builder) {
        this._algorithmSuiteId = builder._algorithmSuiteId;
        this._encryptionContext = builder._encryptionContext;
        this._plaintextDataKey = builder._plaintextDataKey;
    }

    static public Builder builder() {
        return new Builder();
    }

    public int algorithmSuiteId() {
        return _algorithmSuiteId;
    }

    public Map<String, String> encryptionContext() {
        return _encryptionContext;
    }

    public byte[] plaintextDataKey() {
        return _plaintextDataKey;
    }

    public SecretKey dataKey() {
        return new SecretKeySpec(_plaintextDataKey, "AES");
    }

    public Builder toBuilder() {
        return new Builder()
                .algorithmSuiteId(_algorithmSuiteId)
                .encryptionContext(_encryptionContext)
                .plaintextDataKey(_plaintextDataKey);
    }

    static public class Builder {

        private int _algorithmSuiteId = -1;
        private Map<String, String> _encryptionContext = Collections.emptyMap();
        private byte[] _plaintextDataKey = null;

        private Builder() {
        }

        public Builder algorithmSuiteId(int id) {
            _algorithmSuiteId = id;
            return this;
        }

        public Builder encryptionContext(Map<String, String> encryptionContext) {
            _encryptionContext = encryptionContext == null
                    ? Collections.emptyMap()
                    : Collections.unmodifiableMap(encryptionContext);
            return this;
        }

        public Builder plaintextDataKey(byte[] plaintextDataKey) {
            _plaintextDataKey = plaintextDataKey == null ? null : plaintextDataKey.clone();
            return this;
        }

        public DecryptionMaterials build() {
            return new DecryptionMaterials(this);
        }
    }
}
