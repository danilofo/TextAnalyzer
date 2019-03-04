package textAnalyzer;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

public class CloudGenerator {
	//Function to generate graphical output from a sample of document as a tag cloud
	public void generate(Hashtable<String, Double> sample){
		
		JFrame frame = new JFrame(CloudGenerator.class.getSimpleName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		Cloud cloud = new Cloud();
		 
		 

		for (Enumeration<String> e= sample.keys();e.hasMoreElements();){
			String t = e.nextElement();
			Tag tag=new Tag(t, sample.get(t) );
			cloud.addTag(tag);
		}
		 
		 
		 for (Tag tag : cloud.tags()) {
		 final JLabel label = new JLabel(tag.getName());
		 label.setOpaque(false);
		 label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 10));
		 panel.add(label);
		 }
		 frame.add(panel);
		 frame.setSize(800, 600);
		 frame.setVisible(true);			
	}
	
	public static void main (String[] args){
		 final Hashtable<String, Double> sample = Analyzer.sampleAnalysis(TextUtility.normalize(
					TextUtility.readFolder("newSamples/informatica")));
			
			SwingUtilities.invokeLater(new Runnable() {
				 @Override
				 public void run() {
				 new CloudGenerator().generate(sample);
				 }
				 });
		
		}
	
	
}
