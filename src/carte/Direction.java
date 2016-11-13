package carte;

public enum Direction {
	NORD, 
	SUD,
	EST,
	OUEST;

	public static Direction getOppose(Direction dir) {
		switch (dir) {
        case NORD:
        	return SUD;
		case SUD:
        	return NORD;
		case EST:
        	return OUEST;
		case OUEST:
        	return EST;
		default: 
        	throw new IllegalArgumentException("Direction inconnue");
        }		
	}
}
