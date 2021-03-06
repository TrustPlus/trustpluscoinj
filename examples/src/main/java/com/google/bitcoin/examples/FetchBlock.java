/*
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
//import com.google.bitcoin.params.TestNet3Params;
import com.google.bitcoin.params.MainNetParams;
import com.google.bitcoin.store.BlockStore;
import com.google.bitcoin.store.MemoryBlockStore;
import com.google.bitcoin.utils.BriefLogFormatter;

import java.net.InetAddress;
import java.util.concurrent.Future;

/**
 * Downloads the block given a block hash from the localhost node and prints it out.
 */
public class FetchBlock {
    public static void main(String[] args) throws Exception {
        BriefLogFormatter.init();
        System.out.println("Connecting to node");
        //final NetworkParameters params = TestNet3Params.get();
        final NetworkParameters params = MainNetParams.get();

        BlockStore blockStore = new MemoryBlockStore(params);
        BlockChain chain = new BlockChain(params, blockStore);
        PeerGroup peerGroup = new PeerGroup(params, chain);
        peerGroup.startAndWait();
        PeerAddress addr = new PeerAddress(InetAddress.getLocalHost(), params.getPort());
        peerGroup.addAddress(addr);
      
        System.out.println("Waiting for peers. ");  
        peerGroup.waitForPeers(1).get();
        System.out.println("Getting up to 1 connected peers. ");
        Peer peer = peerGroup.getConnectedPeers().get(1);

        System.out.println("");
        System.out.println("");
        System.out.println("Connected to node. Requesting Block: "+args[0]);
        Sha256Hash blockHash = new Sha256Hash(args[0]);
        System.out.println("as block: "+blockHash);
        Future<Block> future = peer.getBlock(blockHash);
        System.out.println("Waiting for node to send us the requested block: " + blockHash);
        Block block = future.get();
        System.out.println(block);
        peerGroup.stop();
    }
}
