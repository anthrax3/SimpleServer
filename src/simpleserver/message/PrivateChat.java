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
package simpleserver.message;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import simpleserver.Color;
import simpleserver.Player;
import simpleserver.PlayerList;

public class PrivateChat extends AbstractChat {

  private Player reciever;

  public PrivateChat(Player sender, Player reciever) {
    super(sender);
    this.reciever = reciever;
    chatRoom = reciever.getName();
  }

  @Override
  public List<Player> getRecievers(PlayerList playerList) {
    List<Player> recieverList = new LinkedList<Player>();
    if (reciever.getName() != null) {
      Collections.addAll(recieverList, sender, reciever);
      reciever.setReply(sender);
    } else {
      recieverList.clear();
    }

    return recieverList;
  }

  @Override
  @Deprecated
  protected boolean sendToPlayer(Player reciever) {
    return (reciever.equals(this.reciever) || reciever.equals(sender));
  }

  @Override
  public void noRecieverFound() {
    sender.addTMessage(Color.RED, "Player not online (%s)", chatRoom);
  }

}
