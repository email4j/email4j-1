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
package desi.juan.email.internal.commands;

import static java.lang.String.format;
import static javax.mail.Flags.Flag;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.UIDFolder;

import com.google.common.collect.Maps;
import desi.juan.email.api.EmailFlags;
import desi.juan.email.api.EmailFlags.EmailFlag;
import desi.juan.email.internal.exception.RetrieveEmailException;

/**
 * Represents an operation that marks an email with one of the {@link EmailFlags}. This operation can only be used on Imap Mailboxes
 * POP3 does not support flags.
 */
public final class MarkEmailCommand {

  private static final Map<EmailFlag, Flag> flagMap;

  static {
    flagMap = Maps.newHashMap();
    flagMap.put(EmailFlag.SEEN, Flag.SEEN);
    flagMap.put(EmailFlag.ANSWERED, Flag.ANSWERED);
    flagMap.put(EmailFlag.RECENT, Flag.RECENT);
    flagMap.put(EmailFlag.DRAFT, Flag.DRAFT);
    flagMap.put(EmailFlag.DELETED, Flag.DELETED);
  }

  public void markById(UIDFolder folder, EmailFlag flag, long uid) {
    try {
      Message message = folder.getMessageByUID(uid);
      message.setFlag(flagMap.get(flag), true);
    } catch (MessagingException e) {
      throw new RetrieveEmailException(format("Error while marking the email of id:[%s] as [%s]", uid, flag));
    }
  }
}
