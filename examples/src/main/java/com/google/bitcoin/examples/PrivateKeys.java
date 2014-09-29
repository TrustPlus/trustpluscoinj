/**
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.bitcoin.examples;

import com.google.bitcoin.core.*;
import com.google.bitcoin.params.MainNetParams;
import com.google.bitcoin.store.MemoryBlockStore;

import java.io.File;
import java.math.BigInteger;
import java.net.InetAddress;

/**
 * This example shows how to solve the challenge Hal posted here:<p>
 *
 * <a href="http://www.bitcoin.org/smf/index.php?topic=3638.0">http://www.bitcoin.org/smf/index.php?topic=3638
 * .0</a><p>
 *
 * in which a private key with some coins associated with it is published. The goal is to import the private key,
 * claim the coins and then send them to a different address.
 */
public class PrivateKeys {
    public static void main(String[] args) throws Exception {
        // TODO: Assumes main network not testnet. Make it selectable.
        System.out.println("");
        System.out.println("===========================");
        System.out.println("Setting Network Parameters.");
        NetworkParameters params = MainNetParams.get();
        System.out.println("Network Parameters Set.");
        System.out.println("===========================");
        System.out.println("");
               
        try {
            // Decode the private key from Satoshis Base58 variant. If 51 characters long then it's from Bitcoins
            // dumpprivkey command and includes a version byte and checksum. Otherwise assume it's a raw key.
            System.out.println("");
            System.out.println("===========================");
            System.out.println("   Decoding Private Key");
            
            ECKey key;
            if (args[0].length() == 51) {
                DumpedPrivateKey dumpedPrivateKey = new DumpedPrivateKey(params, args[0]);
                key = dumpedPrivateKey.getKey();
            } else {
                BigInteger privKey = Base58.decodeToBigInteger(args[0]);
                key = new ECKey(privKey);
            }
            System.out.println("Address from private key is: " + key.toAddress(params).toString());
            // And the address ...
            Address destination = new Address(params, args[1]);
            System.out.println("Destination address is: "+args[1]);

            // Import the private key to a fresh wallet.
            System.out.println("Creating Wallet and adding key: "+key);
            Wallet wallet = new Wallet(params);
            wallet.addKey(key);
            System.out.println("====Displaying wallet====");
            System.out.println(wallet.toString());
            System.out.println("====Displayed wallet====");

            //Save wallet to file.
            File file = new File(args[2]);
            wallet.saveToFile(file);

            System.out.println("Finding Transactions that involve coins associated with address "+key.toAddress(params).toString());
            // Find the transactions that involve those coins.
            final MemoryBlockStore blockStore = new MemoryBlockStore(params);
            BlockChain chain = new BlockChain(params, wallet, blockStore);

            System.out.println("Connecting to PeerGroup.");
            final PeerGroup peerGroup = new PeerGroup(params, chain);
            peerGroup.addAddress(new PeerAddress(InetAddress.getByName("184.173.115.98")));
            peerGroup.addAddress(new PeerAddress(InetAddress.getByName("5.250.177.30")));
            peerGroup.addAddress(new PeerAddress(InetAddress.getByName("159.8.2.42")));
            peerGroup.addAddress(new PeerAddress(InetAddress.getByName("198.100.154.180")));
            peerGroup.addAddress(new PeerAddress(InetAddress.getByName("83.102.59.72")));
            peerGroup.addAddress(new PeerAddress(InetAddress.getByName("98.157.205.240")));
            peerGroup.addAddress(new PeerAddress(InetAddress.getByName("119.81.151.106")));

            peerGroup.start();

            System.out.println("Downloading the block chain.");
            peerGroup.downloadBlockChain();
            peerGroup.stop();

            // Claim them!
            System.out.println("Claiming " + Utils.bitcoinValueToFriendlyString(wallet.getBalance()) + " coins");
            System.out.println("====Displaying wallet====");
            System.out.println(wallet.toString());
            System.out.println("====Displayed wallet====");

            wallet.saveToFile(file);

            //Send a quarter of them to another address!
            System.out.println("====Sending Coins to "+args[1]+"====");
            BigInteger divisor; divisor = new BigInteger ("4");
            wallet.sendCoins(peerGroup, destination, wallet.getBalance().divide(divisor));
            System.out.println("====Sent Coins to "+args[1]+"====");

            // Wait a few seconds to let the packets flush out to the network (ugly).
            Thread.sleep(5000);

            System.out.println("Exiting.");
            System.out.println("===========================");
            System.out.println("");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("First arg should be private key in Base58 format. Second argument should be address " +
                    "to send to.");
            return;
        }
    }
}
