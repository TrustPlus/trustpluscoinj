/*
 * Copyright 2013 Google Inc.
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

package com.google.bitcoin.params;

import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.core.Sha256Hash;
import com.google.bitcoin.core.Utils;
import com.google.bitcoin.core.CoinDefinition;

import javax.xml.bind.DatatypeConverter;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends NetworkParameters {
    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        proofOfWorkLimit = CoinDefinition.proofOfWorkLimit;
        dumpedPrivateKeyHeader = 128 + CoinDefinition.AddressHeader;
        addressHeader = CoinDefinition.AddressHeader;
        p2shHeader = CoinDefinition.p2shHeader;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader};
        port = CoinDefinition.Port;
        packetMagic = CoinDefinition.PacketMagic;
        genesisBlock.setDifficultyTarget(CoinDefinition.genesisBlockDifficultyTarget);
        genesisBlock.setTime(CoinDefinition.genesisBlockTime);
        genesisBlock.setNonce(CoinDefinition.genesisBlockNonce);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = CoinDefinition.subsidyDecreaseBlockCount;
        spendableCoinbaseDepth = CoinDefinition.spendableCoinbaseDepth;

//        System.out.println("block Hex: " + DatatypeConverter.printHexBinary(genesisBlock.unsafeBitcoinSerialize()));
//        System.out.println("Expected : 010000000000000000000000000000000000000000000000000000000000000000000000faff7a1cf6a0272a42ecf70ca603930e980c1f85223e1545da15c66b6b314d88e6b0b553ffff0f1eafac09000101000000e6b0b553010000000000000000000000000000000000000000000000000000000000000000ffffffff0a00012a0634204a756c79ffffffff0100000000000000000000000");

        //Calculate Genesis Block Hash
//        System.out.println("Calculating Genesis Block x11 hash.");
        String genesisHash = genesisBlock.getHashAsString();
        

//        System.out.println("");
//        System.out.println("Block Hash: "+genesisHash);
//        System.out.println("Expected  : 000005eee9ae452766cd6590c53fce4aa523403c121353d9e68a68b18fddcf77");
        //Force genesisHash NEEDS DEBUGGING IF CALCULATING HASH CORRECTLY
        //genesisHash = "000005eee9ae452766cd6590c53fce4aa523403c121353d9e68a68b18fddcf77"; //FORCE CORRECT HASH FOR DEBUGGING
        System.out.println("");
        
        checkState(genesisHash.equals(CoinDefinition.genesisHash), genesisHash);

        CoinDefinition.initCheckpoints(checkpoints);

        dnsSeeds = CoinDefinition.dnsSeeds;

    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }

    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
