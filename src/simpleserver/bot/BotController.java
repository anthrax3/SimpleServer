/*
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package simpleserver.bot;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import simpleserver.Server;

public class BotController {
  private Server server;
  private Map<String, Bot> bots;
  private List<File> garbage;

  public BotController(Server server) {
    this.server = server;
    bots = new HashMap<String, Bot>();
    garbage = new LinkedList<File>();
  }

  public boolean connect(Bot bot) {
    bots.put(bot.name, bot);
    bot.setController(this);
    try {
      bot.connect();
    } catch (Exception e) {
      remove(bot);
      System.out.println("[SimpleServer] Couldn't start bot <" + bot.name + "> (" + bot.getClass().getSimpleName() + ")");
      return false;
    }
    return true;

  }

  void remove(Bot bot) {
    bots.remove(bot.name);
  }

  public void cleanup() {
    for (Bot bot : bots.values()) {
      try {
        bot.logout();
      } catch (IOException e) {
        System.out.println("[SimpleServer] Couldn't stop bot <" + bot.name + "> (" + bot.getClass().getSimpleName() + ")");
      }
    }
    for (File file : garbage) {
      file.delete();
    }
  }

  public boolean ninja(String name) {
    Bot bot = bots.get(name);
    if (bot == null) {
      return false;
    } else {
      return bot.ninja();
    }
  }

  void trash(File file) {
    garbage.add(file);
  }

}
