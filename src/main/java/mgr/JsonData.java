package mgr;
import java.util.List;

public class JsonData {
	public List<HitParsingResult> getHitParsingResults() {
		return hitParsingResults;
	}

	public void setHitParsingResults(List<HitParsingResult> hitParsingResults) {
		this.hitParsingResults = hitParsingResults;
	}

	private List<HitParsingResult> hitParsingResults;
}
