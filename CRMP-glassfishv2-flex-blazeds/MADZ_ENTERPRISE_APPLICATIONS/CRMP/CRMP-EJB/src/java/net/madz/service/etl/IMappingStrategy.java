package net.madz.service.etl;

public interface IMappingStrategy<T> {

	T covert(String rawData);
}
