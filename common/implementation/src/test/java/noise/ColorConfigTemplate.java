package noise;

import com.dfsek.tectonic.annotations.Default;
import com.dfsek.tectonic.annotations.Value;
import com.dfsek.tectonic.config.ConfigTemplate;

import com.dfsek.terra.api.config.meta.Meta;
import com.dfsek.terra.api.util.collection.ProbabilityCollection;


public class ColorConfigTemplate implements ConfigTemplate {
    @Value("enable")
    @Default
    private @Meta boolean enable = false;
    @Value("colors")
    private @Meta ProbabilityCollection<@Meta Integer> colors;
    
    public boolean enable() {
        return enable;
    }
    
    public ProbabilityCollection<Integer> getColors() {
        return colors;
    }
}
