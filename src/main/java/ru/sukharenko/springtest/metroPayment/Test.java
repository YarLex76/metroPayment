package ru.sukharenko.springtest.metroPayment;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.sukharenko.springtest.metroPayment.models.Cards;
import ru.sukharenko.springtest.metroPayment.repositories.CardsRepository;
import ru.sukharenko.springtest.metroPayment.services.CardsServices;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Test {
    public static void main(String[] args){
        CardsServices cardsServices = new CardsServices(new CardsRepository() {
            @Override
            public Optional<Cards> findByEmail(String email) {
                return Optional.empty();
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Cards> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Cards> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Cards> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Cards getOne(Long aLong) {
                return null;
            }

            @Override
            public Cards getById(Long aLong) {
                return null;
            }

            @Override
            public Cards getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Cards> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Cards> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public <S extends Cards> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public List<Cards> findAll() {
                return null;
            }

            @Override
            public List<Cards> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public <S extends Cards> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Cards> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Cards entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends Cards> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public List<Cards> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<Cards> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Cards> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Cards> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Cards> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Cards> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Cards, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        });

    }

}
