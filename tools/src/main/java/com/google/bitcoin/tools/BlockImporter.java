package com.google.bitcoin.tools;

import com.google.bitcoin.core.*;
import com.google.bitcoin.params.MainNetParams;
import com.google.bitcoin.store.*;
import com.google.bitcoin.utils.BlockFileLoader;
import com.google.common.base.Preconditions;

import java.io.File;

/** Very thin wrapper around {@link com.google.bitcoin.utils.BlockFileLoader} */
public class BlockImporter {
    public static void main(String[] args) throws BlockStoreException, VerificationException, PrunedException {
        System.out.println("USAGE: BlockImporter (H2|Disk|MemFull|Mem|SPV) [blockStore]");
        System.out.println("       blockStore is required unless type is Mem or MemFull");
        System.out.println("       eg BlockImporter prod H2 /home/user/bitcoinj.h2store");
        System.out.println("       Does full verification if the store supports it");
        Preconditions.checkArgument(args.length == 1 || args.length == 2);
        
        NetworkParameters params;
        params = MainNetParams.get();
        
        BlockStore store;
        if (args[0].equals("H2")) {
            Preconditions.checkArgument(args.length == 2);
            store = new H2FullPrunedBlockStore(params, args[1], 100);
        } else if (args[0].equals("MemFull")) {
            Preconditions.checkArgument(args.length == 1);
            store = new MemoryFullPrunedBlockStore(params, 100);
        } else if (args[0].equals("Mem")) {
            Preconditions.checkArgument(args.length == 1);
            store = new MemoryBlockStore(params);
        } else if (args[0].equals("SPV")) {
            Preconditions.checkArgument(args.length == 2);
            store = new SPVBlockStore(params, new File(args[1]));
        } else {
            System.err.println("Unknown store " + args[0]);
            return;
        }
        
        AbstractBlockChain chain = null;
        if (store instanceof FullPrunedBlockStore)
            chain = new FullPrunedBlockChain(params, (FullPrunedBlockStore) store);
        else
            chain = new BlockChain(params, store);
        
        BlockFileLoader loader = new BlockFileLoader(params, BlockFileLoader.getReferenceClientBlockFileList());
        
        for (Block block : loader)
            chain.add(block);
    }
}
