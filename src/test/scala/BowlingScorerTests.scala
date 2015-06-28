import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by dylangrald on 6/25/15.
 */
class BowlingScorerTests extends FlatSpec with Matchers {

  val bowlingScorer = BowlingScorer.getInstance

  object BowlingFrame {
    def strike: BowlingFrame = new RegularBowlingFrame(10, 0)
    def gutterBall: BowlingFrame = new RegularBowlingFrame(0, 0)
  }

  "The bowling scorer" should "return 0 when there are all gutter balls" in {
    val gutterBalls = List.fill(10)(BowlingFrame.gutterBall)
    val actualScore = bowlingScorer.calculateScores(gutterBalls)

    assert(actualScore == 0)
  }

  it should "return 60 points in a game where the player bowls 3 strikes followed by gutter balls" in {
    val scores = List(BowlingFrame.strike, BowlingFrame.strike, BowlingFrame.strike) ++ List.fill(7)(BowlingFrame.gutterBall)
    val actualScore = bowlingScorer.calculateScores(scores)

    assert(actualScore == 60)
  }

  it should "return 300 points in a perfect game" in {
    val scores = List.fill(10)(BowlingFrame.strike) :+ new BonusBowlingFrame(10) :+ new BonusBowlingFrame(10)
    val actualScore = bowlingScorer.calculateScores(scores)

    assert(actualScore == 300)
  }

  it should "return 150 for ten straight frames of 5 pins on the first and 5 on the second followed by a bonus roll of 5" in {
    val scores = List.fill(10)(new RegularBowlingFrame(5,5)) :+ new BonusBowlingFrame(5)
    val actualScore = bowlingScorer.calculateScores(scores)

    assert(actualScore == 150)

  }

  it should "return the correct score for a spare followed by a strike" in {
    val scores = List(new RegularBowlingFrame(6,4), BowlingFrame.strike, new RegularBowlingFrame(6,4), BowlingFrame.strike, new RegularBowlingFrame(6,4)) ++ List.fill(5)(BowlingFrame.gutterBall)
    val actualScore = bowlingScorer.calculateScores(scores)

    assert(actualScore == 90)
  }
}
