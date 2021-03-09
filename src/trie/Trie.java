package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }

	static ArrayList<TrieNode> sol = new ArrayList<>();
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/

		TrieNode root = new TrieNode(null,null,null );
		// adding the first node
		TrieNode firstNode = new TrieNode( new Indexes((short)0,(short)0,(short)(allWords[0].length()-1)),null,null );
		root.firstChild = firstNode;

		// creating tree for rest of the nodes
		for(short i=1;i<allWords.length;i++) {
				String word = allWords[i];
				short one,two,three;
				one = i;
				two = 0;
				three = (short)(word.length()-1);

				// traverse the tree

				TrieNode ptr = root.firstChild;

				while (ptr!=null) {

					int mismatch = -1;

					for(int ii=ptr.substr.startIndex;ii<=ptr.substr.endIndex;ii++) {

						if(word.charAt(ii)!=allWords[ptr.substr.wordIndex].charAt(ii)) {
							mismatch = ii;
							break;
						}

					}

					if(mismatch==-1) { // all matched
						two = (short) (ptr.substr.endIndex+1);
						ptr = ptr.firstChild;
						continue;
					}
					else if (mismatch==ptr.substr.startIndex) { // no match
						// check any siblings left
						if(ptr.sibling!=null) {
							ptr = ptr.sibling;
							continue;
						}
						else {
							// add a new node
							TrieNode newNode = new TrieNode(new Indexes(one,two,three),null,null);

							ptr.sibling = newNode;
							break;
						}
					}
					else { // few matched

						// add two new nodes

						TrieNode newNodeR = new TrieNode( new Indexes(one,(short)mismatch,three),null,null );
						TrieNode newNodeL = new TrieNode(new Indexes(ptr.substr.wordIndex,(short)mismatch,ptr.substr.endIndex),ptr.firstChild,null);

						// update node

						ptr.substr.endIndex = (short) (mismatch-1);

						ptr.firstChild = newNodeL;
						newNodeL.sibling = newNodeR;

						break;

					}


				}

				ptr = ptr.sibling;

		}


		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return root;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */




	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/

		sol.clear();

		TrieNode root_node = find_root(root,allWords,prefix);

		if(root_node==null) {
			return null;
		}
		else if(root_node.firstChild==null) {
			sol.add(root_node);
			return sol;
		}
		else {

			find_sol(root_node.firstChild,allWords);
			return sol;
		}




		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION

	}

	private static void find_sol(TrieNode node, String[] allWords) {



		while (node!=null)
		{
			if (node.firstChild!=null)
				find_sol(node.firstChild,allWords);
			else sol.add(node);
			node = node.sibling;
		}


	}

	private static TrieNode find_root(TrieNode node, String[] allWords, String prefix) {


		TrieNode ptr = node.firstChild;

		while (ptr!=null) {

			int mismatch = -1;

			for(int ii=ptr.substr.startIndex;ii<=ptr.substr.endIndex;ii++) {


				if(prefix.charAt(ii)!=allWords[ptr.substr.wordIndex].charAt(ii)) {
					mismatch = ii;
					break;
				}

				if(ii==prefix.length()-1) {
					//done
					return ptr;
				}

			}

			if(mismatch==-1) {
				// full matched,
				ptr = ptr.firstChild;
				continue;
			}

			ptr = ptr.sibling;

		}

		return null;


	}

	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}

	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
