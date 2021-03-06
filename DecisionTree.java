import org.w3c.dom.Attr;

/**
 * This class enables the construction of a decision tree
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */

public class DecisionTree {

	private static class Node<E> {
		E data;
		Node<E>[] children;

		Node(E data) {
			this.data = data;
		}
	}

	Node<VirtualDataSet> root;

	/**
	 * @param data is the training set (instance of ActualDataSet) over which a
	 *             decision tree is to be built
	 */
	public DecisionTree(ActualDataSet data) {
		root = new Node<VirtualDataSet>(data.toVirtual());
		build(root);
	}

	/**
	 * The recursive tree building function
	 * 
	 * @param node is the tree node for which a (sub)tree is to be built
	 */
	@SuppressWarnings("unchecked")
	private void build(Node<VirtualDataSet> node) {
		// WRITE YOUR CODE HERE!

		// 1 - Edge Cases
		if (node.data == null || node.data.numAttributes < 1 || node.data.numRows < 1)
			return;

		// 2 - Base Cases
		if (node.data.numAttributes == 1 && node.data.getAttribute(0).getName().equals("class"))
			return;
		boolean multipleUnqiue = false;
		for (int i = 0; i < node.data.numAttributes; i++) {
			if (node.data.getAttribute(i).getName().equals("class"))
				continue;
			else if (node.data.getUniqueAttributeValues(i).length > 1);
				multipleUnqiue = true;
		}
		if (!multipleUnqiue)
			return;

		// 3 - Recursive Cases
		// 3.1
		Attribute a_max = null;
		double max_gain = 0;
		GainInfoItem[] gains = InformationGainCalculator.calculateAndSortInformationGains(node.data);
		for (GainInfoItem gain : gains){
			if (gain.getGainValue() > max_gain) {
				max_gain = gain.getGainValue();
				a_max = node.data.getAttribute(gain.getAttributeName());
			}
		}
		assert a_max != null;
		System.out.println(a_max.getName());

		// 3.2
		
		

		


	}

	@Override
	public String toString() {
		return toString(root, 0);
	}

	/**
	 * The recursive toString function
	 * 
	 * @param node        is the tree node for which an if-else representation is to
	 *                    be derived
	 * @param indentDepth is the number of indenting spaces to be added to the
	 *                    representation
	 * @return an if-else representation of node
	 */
	private String toString(Node<VirtualDataSet> node, int indentDepth) {
		// WRITE YOUR CODE HERE!
		
		// Remove the following line once you have implemented the method
		return null;
	}

	/**
	 * @param indentDepth is the depth of the indentation
	 * @return a string containing indentDepth spaces; the returned string (composed
	 *         of only spaces) will be used as a prefix by the recursive toString
	 *         method
	 */
	private static String createIndent(int indentDepth) {
		if (indentDepth <= 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < indentDepth; i++) {
			buffer.append(' ');
		}
		return buffer.toString();
	}

	public static void main(String[] args) throws Exception {
	
		StudentInfo.display();

		if (args == null || args.length == 0) {
			System.out.println("Expected a file name as argument!");
			System.out.println("Usage: java DecisionTree <file name>");
			return;
		}

		String strFilename = args[0];

		ActualDataSet data = new ActualDataSet(new CSVReader(strFilename));

		DecisionTree dtree = new DecisionTree(data);

		System.out.println(dtree);
	}
}