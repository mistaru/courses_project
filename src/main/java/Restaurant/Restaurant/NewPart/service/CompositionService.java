package Restaurant.Restaurant.NewPart.service;

import Restaurant.Restaurant.NewPart.model.Ingredients;
import Restaurant.Restaurant.NewPart.repository.CompositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompositionService {

    CompositionRepository compositionRepository;

    public CompositionService(CompositionRepository compositionRepository) {
        this.compositionRepository = compositionRepository;
    }

    List<Ingredients> listIng(int id) {
        return compositionRepository.findListIngredients(id);
    }

}