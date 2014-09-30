package com.google.bitcoin.core;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

/**
 * Updated by Sergio Tafur for TRUSTplus - 08-2014.
 * Originally Created with IntelliJ IDEA.
 * User: Hash Engineering Solutions
 * Date: 5/3/14
 * To change this template use File | Settings | File Templates.
 */
public class CoinDefinition {


    public static final String coinName = "TrustPlus";
    public static final String coinTicker = "TRUST";
    public static final String coinURIScheme = "TrustPlus";
    public static final String cryptsyMarketId = "155"; //Currently there is no Cryptsy Market for TrustPlus. Leaving Darkcoin as default for now.
    public static final String cryptsyMarketCurrency = "BTC"; //Currently there is no Cryptsy Market for TrustPlus. Leaving Darkcoin as default for now.
    public static final String PATTERN_PRIVATE_KEY_START = "6"; //Initial private key.

    public enum CoinPrecision {
        Coins,
        Millicoins,
    }
    public static final CoinPrecision coinPrecision = CoinPrecision.Coins;


    public static final String BLOCKEXPLORER_BASE_URL_PROD = "http://trust.blockexplorer.cc/";    //blockr.io
    public static final String BLOCKEXPLORER_ADDRESS_PATH = "address/";             //blockr.io path
    public static final String BLOCKEXPLORER_TRANSACTION_PATH = "tx/";              //blockr.io path
    public static final String BLOCKEXPLORER_BLOCK_PATH = "block/";                 //blockr.io path
    public static final String BLOCKEXPLORER_BASE_URL_TEST = BLOCKEXPLORER_BASE_URL_PROD;

    public static final String DONATION_ADDRESS = "TZ61XyGw5kHbPBDrZsUJfXoXVxYHEbZdfx";  //Stafur donation TRUST address
    public static final String DONATION_ADDRESS_TESTNET = "";  //Empty on purpose.

    enum CoinHash {
        SHA256,
        scrypt,
        x11
    };
    public static final CoinHash coinPOWHash = CoinHash.x11;

    public static boolean checkpointFileSupport = true; //false;

    public static final int TARGET_TIMESPAN = (int)(1 * 45);  // 45 seconds per difficulty cycle, on average.
    public static final int TARGET_SPACING = (int)(1 * 45);  // 45 seconds per block.
    public static final int INTERVAL = TARGET_TIMESPAN / TARGET_SPACING;  //1 blocks
//    public static final int INTERVAL = (int) 51000;  //Checkpoint every 51000 blocks

    public static final int getInterval(int height, boolean testNet) {
            return INTERVAL;      //1
    }
    public static final int getIntervalCheckpoints() {
            return INTERVAL;

    }
    public static final int getTargetTimespan(int height, boolean testNet) {
            return TARGET_TIMESPAN;    //45 secs
    }

    public static int spendableCoinbaseDepth = 20; //main.cpp: nt nCoinbaseMaturity = 20;
    public static final BigInteger MAX_MONEY = BigInteger.valueOf(250000000).multiply(Utils.COIN); //main.h:  MAX_MONEY

    public static final BigInteger DEFAULT_MIN_TX_FEE = BigInteger.valueOf(1000);   // MIN_TX_FEE
    public static final BigInteger DUST_LIMIT = BigInteger.valueOf(1000); //main.h CTransaction::GetMinFee        0.01 coins

    public static final int PROTOCOL_VERSION = 60013;          //version.h PROTOCOL_VERSION
    public static final int MIN_PROTOCOL_VERSION = 209;        //version.h MIN_PROTO_VERSION
    public static final int BIP0031_VERSION = 60000;

    public static final int BLOCK_CURRENTVERSION = 1;   //CBlock::CURRENT_VERSION
    public static final int MAX_BLOCK_SIZE = 1000000;
    

    public static final boolean supportsBloomFiltering = false; //Requires PROTOCOL_VERSION 70000 in the client

    public static final int Port    = 36999;       //protocol.h GetDefaultPort(testnet=false)
    public static final int TestPort = 37000;     //protocol.h GetDefaultPort(testnet=true)

    //
    //  Production
    //
    public static final int AddressHeader = 65;             //base58.h CBitcoinAddress::PUBKEY_ADDRESS
    public static final int p2shHeader = 28;             //base58.h CBitcoinAddress::SCRIPT_ADDRESS
    public static final boolean allowBitcoinPrivateKey = false; //for backward compatibility with previous version of digitalcoin
    public static final int dumpedPrivateKeyHeader = 128;   //common to all coins
    public static final long PacketMagic = 0xa1a0a2a3; //Taken from pchMessageStart[4] in main.cpp 0xa1, 0xa0, 0xa2, 0xa3

    //Genesis Block Information from main.cpp: LoadBlockIndex //STOPPED HERE NEED TO FIX for TRUSTPLUS:

    //TRUSTplus Genesis Block Information:
    //Expected hashes for genesis block and genesis transaction merkle root.
    static public String genesisHash = "000005eee9ae452766cd6590c53fce4aa523403c121353d9e68a68b18fddcf77"; //main.cpp: hashGenesisBlock
    static public String genesisMerkleRoot = "884d316b6bc615d645153e22851f0c980e9303a60cf7ec422a27a0f61c7afffa";
    //Parameters used to generate generate Genesis block and genesis transaction merkle root hashes.
    static public long genesisBlockDifficultyTarget = (0x1e0fffffL);         //main.cpp: LoadBlockIndex
    static public long genesisBlockTime = 1404416230L;                       //main.cpp: LoadBlockIndex
    static public long genesisTransactionTime = 1404416230L;
    static public long genesisBlockNonce = (634031);                         //main.cpp: LoadBlockIndex
    static public int genesisBlockValue = 0;                                 //main.cpp: LoadBlockIndex
    static public String genesisTxInBytes = "00012a0634204a756c79";   //Raw script sig in. Taken from block 0 in block explorer
    static public String genesisTxOutBytes = ""; //Raw script sig out. Take from block 0 in block explorer.


    //Darkcoin Genesis Block Information:
    //Expected hashes for genesis block and genesis merkle root.
    //static public String genesisHash = "00000ffd590b1485b3caadc19b22e6379c733355108f107a430458cdf3407ab6"; //main.cpp: hashGenesisBlock
    //static public String genesisMerkleRoot = "e0028eb9648db56b1ac77cf090b99048a8007e2bb64b68f092c03c7f56a662c7";
    //Parameters used to generate generate Genesis block and genesis transaction merkle root hashes.
    //static public long genesisBlockDifficultyTarget = (0x1e0ffff0L);         //main.cpp: LoadBlockIndex
    //static public long genesisBlockTime = 1390095618L;                       //main.cpp: LoadBlockIndex
    //static public long genesisBlockNonce = (28917698);                         //main.cpp: LoadBlockIndex
    //static public int genesisBlockValue = 50;                                                              //main.cpp: LoadBlockIndex
    ////taken from the raw data of the block explorer
    //static public String genesisTxInBytes = "04ffff001d01044c5957697265642030392f4a616e2f3230313420546865204772616e64204578706572696d656e7420476f6573204c6976653a204f76657273746f636b2e636f6d204973204e6f7720416363657074696e6720426974636f696e73";   //"limecoin se convertira en una de las monedas mas segura del mercado, checa nuestros avances"
    //static public String genesisTxOutBytes = "040184710fa689ad5023690c80f3a49c8f13f8d45b8c857fbcbc8bc4a8e4d3eb4b10f4d4604fa08dce601aaf0f470216fe1b51850b4acf21b179c45070ac7b03a9";

    //net.cpp strDNSSeed
    static public String[] dnsSeeds = new String[] {
            //"127.0.0.1",
            "MitchellMint.com",
            "184.173.115.98",
            "5.250.177.30",
            "159.8.2.42",
            "198.100.154.180",
            "83.102.59.72",
            "98.157.205.240",
            "119.81.151.106",
    };

    public static int minBroadcastConnections = 0;   //0 for default; we need more peers.

    //
    // TestNet - dimecoin - not tested
    //
    //public static final boolean supportsTestNet = false;
    //public static final int testnetAddressHeader = 65;             //base58.h CBitcoinAddress::PUBKEY_ADDRESS_TEST
    //public static final int testnetp2shHeader = 28;             //base58.h CBitcoinAddress::SCRIPT_ADDRESS_TEST
    //public static final long testnetPacketMagic = 0xa1a0a2a3;      //
    //public static final String testnetGenesisHash = "000005eee9ae452766cd6590c53fce4aa523403c121353d9e68a68b18fddcf77";
    //static public long testnetGenesisBlockDifficultyTarget = (0x1e0fffffL);         //main.cpp: LoadBlockIndex
    //static public long testnetGenesisBlockTime = 1404416230L;                       //main.cpp: LoadBlockIndex
    //static public long testnetGenesisBlockNonce = (634031L);                         //main.cpp: LoadBlockIndex
    
    public static final boolean supportsTestNet = false; //true;
    public static final int testnetAddressHeader = 111;             //base58.h CBitcoinAddress::PUBKEY_ADDRESS_TEST
    public static final int testnetp2shHeader = 196;             //base58.h CBitcoinAddress::SCRIPT_ADDRESS_TEST
    public static final long testnetPacketMagic = 0xcee2caff;      //
    public static final String testnetGenesisHash = "00000bafbc94add76cb75e2ec92894837288a481e5c005f6563d91623bf8bc2c";
    static public long testnetGenesisBlockDifficultyTarget = (0x1e0ffff0L);         //main.cpp: LoadBlockIndex
    static public long testnetGenesisBlockTime = 1390666206L;                       //main.cpp: LoadBlockIndex
    static public long testnetGenesisBlockNonce = (3861367235L);                         //main.cpp: LoadBlockIndex


    //main.cpp GetBlockValue(height, fee)
    public static final BigInteger GetBlockReward(int height)
    {
        int COIN = 1;
        BigInteger nSubsidy = Utils.toNanoCoins(100, 0);
        if (height == 1)
            nSubsidy = Utils.toNanoCoins(420000, 0);
        return nSubsidy;
    }

    public static int subsidyDecreaseBlockCount = 4730400;     //main.cpp GetBlockValue(height, fee)

    public static BigInteger proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);  //main.cpp bnProofOfWorkLimit (~uint256(0) >> 20); // digitalcoin: starting difficulty is 1 / 2^12

    static public String[] testnetDnsSeeds = new String[] {
            "0.0.0.0",
    };
    //from alert.cpp: CAlert::CheckSignature
    public static final String SATOSHI_KEY = "04B4E3C86CBB37515D61852F3F08E665324EE2513255EE69E3EB171A4F7A9D23CE56BD23352F6538B2855E0916A09AEBDF4B661CFA919FC726F3BB63EC62619FFB";
    public static final String TESTNET_SATOSHI_KEY = "049236256689E39E7323C4BC92FBDCA9EC8C2D115B96B2BCC55F87A857E59D48B1C3654F5F6F0257399E69ED1F3D08DCE555DD421EA4570B119C92C038DA083956";


    //public static final String SATOSHI_KEY = "048240a8748a80a286b270ba126705ced4f2ce5a7847b3610ea3c06513150dade2a8512ed5ea86320824683fc0818f0ac019214973e677acd1244f6d0571fc5103";
    //public static final String TESTNET_SATOSHI_KEY = "04517d8a699cb43d3938d7b24faaff7cda448ca4ea267723ba614784de661949bf632d6304316b244646dea079735b9a6fc4af804efb4752075b9fe2245e14e412";

    /** The string returned by getId() for the main, production network where people trade things. */
    public static final String ID_MAINNET = "org.trustplus.production";
    /** The string returned by getId() for the testnet. */
    public static final String ID_TESTNET = "org.trustplus.test";
    /** Unit test network. */
    public static final String ID_UNITTESTNET = "com.google.trustplus.unittest";

    //checkpoints.cpp Checkpoints::mapCheckpoints
    public static void initCheckpoints(Map<Integer, Sha256Hash> checkpoints)
    {
//        checkpoints.put( 1000, new Sha256Hash("00000000000fdf06aa38f0f132cbf4475dd8b3eb5d0c62c85226d2be313acd67"));
        checkpoints.put( 51000, new Sha256Hash("847a903ed586ecf68263028e6fd33c65dd68c12f1cbf5fd03d2fdad445fd3811"));
        checkpoints.put( 101000, new Sha256Hash("d732e76f2844958ad6281babf65d28194fad97b52fdff342ddd92e01895c9528"));
        checkpoints.put( 151000, new Sha256Hash("ed4f9f7bc9ab0c6888fe9915eed0398f8550ce725eaff8ca8251a6d45c733292"));
        checkpoints.put( 201000, new Sha256Hash("a21882cebf00f37a6e9056c135e0d552333943494d2dc2f61965db96249d187b"));
    }

    //Unit Test Information
    public static final String UNITTEST_ADDRESS = "TWGcdCQ9Ns5ed91td4mpvD2JpbBebJYkPz";
    public static final String UNITTEST_ADDRESS_PRIVATE_KEY = "VdHKGyyoYhcrY3q1Qvz6tBsKNP39GDX46VuCN5G6LF4TdWctM8nq";
}
