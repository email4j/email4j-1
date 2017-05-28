/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Juan Desimoni
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package desi.juan.email.api.client.configuration;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
//import java.util.Optional;
import com.google.common.base.Optional;
import desi.juan.email.api.security.TlsConfiguration;

/**
 * Default implementation of a {@link ClientConfiguration}.
 */
public class DefaultClientConfiguration implements ClientConfiguration {

  private final TlsConfiguration tlsConfig;
  private final long connectionTimeout;
  private final long writeTimeout;
  private final long readTimeout;
  private final Map<String, String> properties;

  DefaultClientConfiguration(long connectionTimeout,
                             long writeTimeout,
                             long readTimeout,
                             Map<String, String> properties,
                             TlsConfiguration tlsConfig) {
    this.tlsConfig = tlsConfig;
    this.connectionTimeout = connectionTimeout;
    this.writeTimeout = writeTimeout;
    this.readTimeout = readTimeout;
    this.properties = ImmutableMap.copyOf(properties);
  }

  @Override
  public Optional<TlsConfiguration> getTlsConfig() {
//    return Optional.ofNullable(tlsConfig);
    return Optional.fromNullable(tlsConfig);
  }

  @Override
  public long getConnectionTimeout() {
    return connectionTimeout;
  }

  @Override
  public long getWriteTimeout() {
    return writeTimeout;
  }

  @Override
  public long getReadTimeout() {
    return readTimeout;
  }

  @Override
  public Map<String, String> getProperties() {
    return properties;
  }
}
